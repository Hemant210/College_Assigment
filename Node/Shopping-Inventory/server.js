const express = require('express');
const session = require('express-session');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const Owner = require('./models/Owner');
const Product = require('./models/Product');

const app = express();

//Connection
mongoose.connect('mongodb://localhost:27017/inventorydb')
.then(() => console.log("MongoDB Connected"))
.catch(err => console.error("Mongo error : ", err));

//Middleware + Session
app.set('view engine', 'ejs');

app.use(express.urlencoded({ extended: true}));
app.use(express.json());

app.use(session({
    secret:'owner_secret_key',
    resave: false,
    saveUninitialized: false,
    cookie: { maxAge : 1000*60*60}
}));

//Login protection
function isloggoedIn(req, res, next){
    if(req.session.owner) return next();
    res.redirect('/login');
}

//Home + Register
app.get('/', (req, res) => {
    res.redirect('/login');
});

app.get('/register', (req, res) => {
    res.render('register', {error: null});
});

//Registration POST
// req.body
//    ↓
// validate
//    ↓
// find existing email
//    ↓
// bcrypt.hash()
//    ↓
// Owner.create()
//    ↓
// redirect login
app.post('/register', async (req, res) => {
    const { name, email, password } = req.body;

    const exists = await Owner.findOne({ email });

    if(exists)
        return res.send("Email Already Exists");

    const hash = await bcrypt.hash(password, 10);

    await Owner.create({
        name, email, password: hash
    });

        res.redirect('/login');
});

//Login
// findOne()
//  ↓
// bcrypt.compare()
//  ↓
// req.session.owner
//  ↓
// /dashboard
app.get('/login', (req, res) => {
    res.render('login', {
        error: null,
        success: null
    });
});


app.post('/login', async (req, res) => {
    const { email, password } = req.body;

    const own = await Owner.findOne({ email });

    if(!own)
        return res.send("Invalid Login");

    const ok = await bcrypt.compare(
        password,
        own.password
    );

    if(!ok)
        return res.send("Invalid Login");

    req.session.owner = own;

    res.redirect('/dashboard');
});

app.get('/dashboard', isloggoedIn, async (req, res) => {
    const products = await Product.find();

    res.render('dashboard', {
        owner: req.session.owner,
        products
    });
});

app.get('/logout', (req, res) => {
    req.session.destroy(() => {
        res.redirect('/login');
    });
});

app.post('/api/products', async (req, res) => {
    const { name, price, quantity } = req.body;

    const product = await Product.create({
        name, price, quantity
    });

    res.json({
        success: true,
        data: product
    });
});


app.get('/api/products', async (req, res) => {
   const products = await Product.find();

    res.json({
        success: true,
        data: products
    });
});

app.listen(3000, () => {

    console.log("Server running at http://localhost:3000");

});

