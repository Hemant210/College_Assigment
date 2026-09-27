const express = require('express');
const session = require('express-session');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const Owner = require('./models/Owner');
const Product = require('./models/Product');

const app = express();


// Database
mongoose.connect('mongodb://127.0.0.1:27017/inventorydb');


// Middleware
app.set('view engine', 'ejs');

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use(session({
    secret: 'secret',
    resave: false,
    saveUninitialized: false
}));


// Login Protection
function isLoggedIn(req, res, next) {

    if (req.session.owner)
        next();
    else
        res.redirect('/login');
}


// Home
app.get('/', (req, res) => {
    res.redirect('/login');
});


// Register
app.get('/register', (req, res) => {
    res.render('register');
});

app.post('/register', async (req, res) => {

    const { name, email, password } = req.body;

    const exists = await Owner.findOne({ email });

    if (exists)
        return res.send('Email Already Exists');

    const hash = await bcrypt.hash(password, 10);

    await Owner.create({
        name,
        email,
        password: hash
    });

    res.redirect('/login');
});


// Login
app.get('/login', (req, res) => {
    res.render('login');
});

app.post('/login', async (req, res) => {

    const { email, password } = req.body;

    const owner = await Owner.findOne({ email });

    if (!owner)
        return res.send('Invalid Login');

    const ok = await bcrypt.compare(password, owner.password);

    if (!ok)
        return res.send('Invalid Login');

    req.session.owner = owner;

    res.redirect('/dashboard');
});


// Dashboard
app.get('/dashboard', isLoggedIn, async (req, res) => {

    const products = await Product.find();

    res.render('dashboard', {
        owner: req.session.owner,
        products
    });
});


// Logout
app.get('/logout', (req, res) => {

    req.session.destroy(() => {
        res.redirect('/login');
    });

});


// POST API
app.post('/api/products', async (req, res) => {

    const { name, price, quantity } = req.body;

    const product = await Product.create({
        name,
        price,
        quantity
    });

    res.json({
        success: true,
        data: product
    });
});


// GET API
app.get('/api/products', async (req, res) => {

    const products = await Product.find();

    res.json({
        success: true,
        data: products
    });
});


// Start Server
app.listen(3000, () => {
    console.log('Server running');
});