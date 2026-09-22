const express = require('express');
const mongoose = require('mongoose');
const session = require('express-session');
const bcrypt = require('bcryptjs');
const Student = require('./models/Student');
 
const app = express();

mongoose.connect('mongodb://localhost:27017/StudentDB')
.then(() => console.log("MongoDB connected"))
.catch(err => console.error("MongoDB Error", err));

// ---------- Middleware ----------

app.set("view engine", "ejs");

app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.use(
    session({
        secret: "student_secret_key",
        resave: false,
        saveUninitialized: false,
        cookie: {
            maxAge: 60 * 60 * 1000 // 1 hour
        }
    })
);


// ---------- Login Check ----------

function isLoggedIn(req, res, next) {

    if (req.session.student) {
        next();
    } else {
        res.redirect("/login");
    }
}


// ---------- Home ----------

app.get("/", (req, res) => {
    res.redirect("/login");
});


// ---------- Register Page ----------

app.get("/register", (req, res) => {
    res.render("register", {
        error: null
    });
});


// ---------- Register Student ----------

app.post("/register", async (req, res) => {

    try {

        const name = req.body.name;
        const email = req.body.email;
        const password = req.body.password;

        // Check empty fields
        if (!name || !email || !password) {
            return res.render("register", {
                error: "All fields are required"
            });
        }

        // Check email already exists
        const student = await Student.findOne({
            email: email.toLowerCase()
        });

        if (student) {
            return res.render("register", {
                error: "Email already registered"
            });
        }

        // Encrypt password
        const hashedPassword = await bcrypt.hash(password, 10);

        // Save student
        await Student.create({
            name: name,
            email: email.toLowerCase(),
            password: hashedPassword
        });

        // Go to login
        res.redirect("/login?registered=1");

    } catch (error) {

        res.render("register", {
            error: "Registration failed"
        });
    }
});


// ---------- Login Page ----------

app.get("/login", (req, res) => {

    res.render("login", {
        error: null,
        success: req.query.registered
            ? "Registration successful. Please login."
            : null
    });
});


// ---------- Login Student ----------

app.post("/login", async (req, res) => {

    try {

        const email = req.body.email;
        const password = req.body.password;

        // Find student
        const student = await Student.findOne({
            email: (email || "").toLowerCase()
        });

        // Check email and password
        if (!student) {
            return res.render("login", {
                error: "Invalid email or password",
                success: null
            });
        }

        const correctPassword = await bcrypt.compare(
            password || "",
            student.password
        );

        if (!correctPassword) {
            return res.render("login", {
                error: "Invalid email or password",
                success: null
            });
        }

        // Store student in session
        req.session.student = {
            id: student._id,
            name: student.name,
            email: student.email
        };

        // Open dashboard
        res.redirect("/dashboard");

    } catch (error) {

        res.render("login", {
            error: "Login failed",
            success: null
        });
    }
});


// ---------- Dashboard ----------

app.get("/dashboard", isLoggedIn, (req, res) => {

    res.render("dashboard", {
        student: req.session.student
    });
});


// ---------- Logout ----------

app.get("/logout", (req, res) => {

    req.session.destroy(() => {

        res.clearCookie("connect.sid");

        res.redirect("/login");
    });
});


// =====================================================
// REST API
// =====================================================


// ---------- POST: Add Student ----------

app.post("/api/students", async (req, res) => {

    try {

        const name = req.body.name;
        const email = req.body.email;
        const password = req.body.password;

        // Check fields
        if (!name || !email || !password) {

            return res.status(400).json({
                success: false,
                message: "Name, email and password are required"
            });
        }

        // Check email
        const exists = await Student.findOne({
            email: email.toLowerCase()
        });

        if (exists) {

            return res.status(409).json({
                success: false,
                message: "Email already exists"
            });
        }

        // Encrypt password
        const hashedPassword = await bcrypt.hash(password, 10);

        // Save student
        const student = await Student.create({
            name: name,
            email: email.toLowerCase(),
            password: hashedPassword
        });

        // Send response
        res.status(201).json({
            success: true,
            message: "Student saved",
            data: {
                id: student._id,
                name: student.name,
                email: student.email
            }
        });

    } catch (error) {

        res.status(500).json({
            success: false,
            message: error.message
        });
    }
});


// ---------- GET: Get All Students ----------
// Password is NOT returned

app.get("/api/students", async (req, res) => {

    try {

        const students = await Student.find(
            {},
            "name email"
        );

        res.json({
            success: true,
            count: students.length,
            data: students
        });

    } catch (error) {

        res.status(500).json({
            success: false,
            message: error.message
        });
    }
});


// ---------- Start Server ----------

app.listen(3000, () => {

    console.log("Server running at http://localhost:3000");

});

