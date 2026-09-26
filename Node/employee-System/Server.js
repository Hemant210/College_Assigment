const mongoose = require("mongoose");
const express = require("express");
const session = require("express-session");
const bcrypt = require("bcryptjs");

const Employee = require("./models/employee");

const app = express();

// ================= MONGODB =================

mongoose
    .connect("mongodb://127.0.0.1:27017/employeedb")
    .then(() => console.log("MongoDB Connected"))
    .catch((err) => console.log(err));

// ================= MIDDLEWARE =================

app.set("view engine", "ejs");

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use(
    session({
        secret: "secret123",
        resave: false,
        saveUninitialized: false
    })
);

// ================= LOGIN PROTECTION =================

function islogin(req, res, next) {

    if (req.session.employee) {
        next();
    } else {
        res.redirect("/login");
    }

}

// ================= HOME =================

app.get("/", (req, res) => {
    res.redirect("/login");
});

// ================= REGISTER =================

app.get("/register", (req, res) => {

    res.render("register", {
        error: null
    });

});

app.post("/register", async (req, res) => {

    const { name, email, password, department } = req.body;

    if (!name || !email || !password || !department) {

        return res.render("register", {
            error: "All Fields Required"
        });

    }

    const exists = await Employee.findOne({
        email: email
    });

    if (exists) {

        return res.render("register", {
            error: "Email already exists"
        });

    }

    const hashedPassword = await bcrypt.hash(password, 10);

    await Employee.create({
        name: name,
        email: email,
        password: hashedPassword,
        department: department
    });

    res.redirect("/login");

});

// ================= LOGIN =================

app.get("/login", (req, res) => {

    res.render("login", {
        error: null
    });

});

app.post("/login", async (req, res) => {

    const { email, password } = req.body;

    const emp = await Employee.findOne({
        email: email
    });

    if (!emp) {

        return res.render("login", {
            error: "Invalid Email and Password"
        });

    }

    const match = await bcrypt.compare(
        password,
        emp.password
    );

    if (!match) {

        return res.render("login", {
            error: "Invalid Email and Password"
        });

    }

    req.session.employee = {

        name: emp.name,

        email: emp.email,

        department: emp.department

    };

    res.redirect("/dashboard");

});

// ================= DASHBOARD =================

app.get("/dashboard", islogin, (req, res) => {

    res.render("dashboard", {
        employee: req.session.employee
    });

});

// ================= LOGOUT =================

app.get("/logout", (req, res) => {

    req.session.destroy(() => {

        res.redirect("/login");

    });

});

// ================= API POST =================

app.post("/api/employees", async (req, res) => {

    try {

        const {
            name,
            email,
            password,
            department
        } = req.body;

        if (!name || !email || !password || !department) {

            return res.status(400).json({
                message: "All Fields Required"
            });

        }

        const exists = await Employee.findOne({
            email: email
        });

        if (exists) {

            return res.status(409).json({
                message: "Email already exists"
            });

        }

        const hashedPassword = await bcrypt.hash(
            password,
            10
        );

        const emp = await Employee.create({

            name: name,

            email: email,

            password: hashedPassword,

            department: department

        });

        res.status(201).json({

            message: "Employee Saved",

            data: {

                id: emp._id,

                name: emp.name,

                email: emp.email,

                department: emp.department

            }

        });

    } catch (err) {

        res.status(500).json({
            message: err.message
        });

    }

});

// ================= API GET =================

app.get("/api/employees", async (req, res) => {

    try {

        const employees = await Employee.find(
            {},
            "name email department"
        );

        res.json({
            data: employees
        });

    } catch (err) {

        res.status(500).json({
            message: err.message
        });

    }

});

// ================= START SERVER =================

app.listen(3000, () => {

    console.log(
        "Server running on http://localhost:3000"
    );

});
