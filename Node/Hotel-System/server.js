const mongoose = require("mongoose");
const express = require("express");
const session = require("express-session");
const bcrypt = require("bcryptjs");

const Guest = require("./models/Guest");
const app = express();

mongoose
  .connect("mongodb://localhost:27017/hotelDB")
  .then(() => console.log("MongoDB Connected"))
  .catch((err) => console.log(err));

app.set("view engine", "ejs");
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use(
  session({
    secret: "secret",
    resave: false,
    saveUninitialized: false,
  }),
);

function isLoggedIn(req, res, next) {
  if (req.session.guest) {
    next();
  } else {
    res.redirect("/login");
  }
}

app.get("/", (req, res) => {
  res.redirect("/login");
});

//Register
app.get("/register", (req, res) => {
  res.render("register", {
    error: null,
  });
});

app.post("/register", async (req, res) => {
  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.render("register", {
      error: "All Field required",
    });
  }

  const exists = await Guest.findOne({ email });

  if (exists) {
    return res.render("register", {
      error: "Email Already Exits",
    });
  }

  const hash = await bcrypt.hash(password, 10);

  await Guest.create({
    name,
    email,
    password: hash,
  });

  res.redirect("/login");
});

//Login
app.get("/login", (req, res) => {
  res.render("login", {
    error: null,
  });
});

app.post("/login", async (req, res) => {
  const { email, password } = req.body;

  const guest = await Guest.findOne({ email });

  if (!guest) {
    return res.render("login", {
      error: "Email Already Exits",
    });
  }

  const match = await bcrypt.compare(password, guest.password);

  if (!match) {
    return res.render("login", {
      error: "Invalid Email and Password",
    });
  }

  req.session.guest = {
    id: guest._id,
    name: guest.name,
    email: guest.email,
  };
  res.redirect("/dashboard");
});

//Dashboard
app.get("/dashboard", isLoggedIn, (req, res) => {
  res.render("dashboard", {
    guest: req.session.guest,
  });
});

//Destroy
app.get("/logout", (req, res) => {
  req.session.destroy(() => {
    res.redirect("/login");
  });
});

//PHP
app.post("/api/guests", async (req, res) => {
  const { name, email, password } = req.body;

  if (!name || !email || !password) {
    return res.json({
      success: false,
      message: "All Field required",
    });
  }

  const exists = await Guest.findOne({ email });

  if (exists) {
    return res.json({
      success: false,
      message: "email already exists",
    });
  }

  const hash = await bcrypt.hash(password, 10);

  const guest = await Guest.create({
    name,
    email,
    password: hash,
  });

  res.json({
    success: true,
    message: "Data Saved",
    data: {
      name: guest.name,
      email: guest.email,
    },
  });
});

app.get("/api/guests", async (req, res) => {
  const guests = await Guest.find({}, "name email");

  res.json({
    success: true,
    data: guests,
  });
});

app.listen(3000, () => {
  console.log("Server runnig on http://localhost:3000");
});
