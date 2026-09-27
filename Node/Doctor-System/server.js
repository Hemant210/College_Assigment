const express = require("express");
const session = require("express-session");
const bcrypt = require("bcryptjs");
const mongoose = require("mongoose");

const Doctor = require("./models/Doctor");

const app = express();

app.set('view engine', 'ejs');

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use(
  session({
    secret: "secret",
    resave: false,
    saveUninitialized: false,
  }),
);

mongoose
  .connect("mongodb://localhost:27017/doctordb")
  .then(() => console.log("MongoDB Connected"))
  .catch((err) => console.log(err));

function isLoggedIn(req, res, next) {
  if (req.session.doctor) next();
  else res.redirect("/login");
}

app.get("/", (req, res) => {
  res.redirect("/login");
});

app.get("/register", (req, res) => {
  res.render("register");
});

app.post("/register", async (req, res) => {
  const { name, email, password } = req.body;

  const exists = await Doctor.findOne({ email });
  if (exists) return res.send("Email Already Exist");

  const hash = await bcrypt.hash(password, 10);

  await Doctor.create({
    name,
    email,
    password: hash,
  });

  res.redirect("/login");
});

app.get("/login", (req, res) => {
  res.render("login");
});

app.post("/login", async (req, res) => {
  const { email, password } = req.body;

  const doctor = await Doctor.findOne({ email });

  if (!doctor) return res.send("Invalid Login");

  const ok = await bcrypt.compare(password, doctor.password);

  if (!ok) return res.send("Invalid Login");

  req.session.doctor = doctor;

  res.redirect("/dashboard");
});

app.get("/dashboard", isLoggedIn, async (req, res) => {
  const doctors = await Doctor.find();

  res.render("dashboard", {
    doctor: req.session.doctor,
    doctors,
  });
});

app.get("/logout", (req, res) => {
  req.session.destroy(() => {
    res.redirect("/login");
  });
});

app.post("/api/doctors", async (req, res) => {
  const { name, email, password } = req.body;

  const doctor = await Doctor.create({
    name,
    email,
    password,
  });

  res.json({
    success: true,
    data: doctor,
  });
});

app.get("/api/doctors", async (req, res) => {
  const doctors = await Doctor.find();

  res.json({
    success: true,
    data: doctors,
  });
});


app.listen(5000, () => {
    console.log("Server running http://localhost:5000/")
});