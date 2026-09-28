const express = require("express");
const session = require("express-session");
const mongoose = require("mongoose");
const bcrypt = require("bcryptjs");

const Member = require("./model/Member");

const app = express();

//Database Connected
mongoose
  .connect("mongodb://localhost:27017/libraryDB")
  .then(() => console.log("MongoDB Connected"))
  .catch((err) => console.log(err));

//MiddleWare
app.set("view engine", "ejs");
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use(
  session({
    secret: "library-secret",
    resave: false,
    saveUninitialized: false,
  }),
);

//Check Session
function isLoggedIn(req, res, next) {
  if (req.session.member) {
    next();
  } else {
    res.redirect("/login");
  }
}

//Home
app.get("/", (req, res) => {
  res.redirect("/login");
});

//Register
app.get("/register", (req, res) => {
  res.render('register', {
    error: null
  });
});

app.post("/register", async (req, res) => {
  const { name, email, password } = req.body;
  if (!name || !email || !password) {
    return res.render("register", {
      error: "All Field Required",
    });
  }

  const exists = await Member.findOne({ email });

  if (exists) {
    return res.render("register", {
      error: "Email Already register",
    });
  }

  const hash = await bcrypt.hash(password, 10);

  await Member.create({
    name,
    email,
    password: hash,
  });
  res.redirect("/login");
});

//Login
app.get("/login", (req, res) => {
  res.render('login', {
    error: null,
  });
});

app.post("/login", async (req, res) => {
  const { email, password } = req.body;
  const member = await Member.findOne({ email });

  if (!member) {
    return res.render("login", {
      error: "Invalid Email or Password",
    });
  }

  const match = await bcrypt.compare(
    password,
    member.password
  );

    if (!match) {
    return res.render("login", {
      error: "Invalid Email or Password",
    });
  }

  req.session.member = {
    id: member._id,
    name: member.name,
    email: member.email
  };

  res.redirect('/dashboard');
});

//Dashboard
app.get("/dashboard",isLoggedIn, (req, res) => {
  res.render('dashboard', {
    member: req.session.member
  });
});


//Logout
app.get("/logout", (req, res) => {
  req.session.destroy(() => {
      res.redirect('/login');
  });
});

//PHP API
//POST PHP Member
app.post("/api/members", async (req, res) => {
  const { name, email, password } = req.body;
  if (!name || !email || !password) {
    return res.json({
      success: false,
      message: "All Field Required"
    });
  }

  const exists = await Member.findOne({ email });

  if (exists) {
    return res.json("register", {
              success: false,

      message: "Email Already register"
    });
  }

  const hash = await bcrypt.hash(password, 10);

  const member = await Member.create({
    name,
    email,
    password: hash,
  });

  res.json({
    success: true,
    message: "Member Saved",
    data:{
        name: member.name,
        email: member.email
    }
  });
});

//GET 
app.get('/api/members', async(req, res) => {
    const members = await Member.find(
        {},
        'name email'
    );

    res.json({
      success: true,
      data: members
    });
});

app.listen(3000, () => {
    console.log("Server running on http://localhost:3000");
});