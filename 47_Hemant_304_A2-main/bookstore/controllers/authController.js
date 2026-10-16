const User = require('../models/User');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');
const { JWT_SECRET } = require('../middleware/auth');

/**
 * Question 2: Secure Signup for Bookstore Customers
 * Hashes the customer's password using bcrypt before storing it in MongoDB.
 */
const signup = async (req, res) => {
  try {
    const { name, email, password, role } = req.body;

    // Check if user already exists
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({
        success: false,
        message: 'A user with this email already exists',
      });
    }

    // Notice: Due to the Mongoose pre-save hook on User schema,
    // password hashing is done automatically.
    // Alternatively, explicit hashing:
    // const salt = await bcrypt.genSalt(10);
    // const hashedPassword = await bcrypt.hash(password, salt);

    const user = new User({
      name,
      email,
      password, // Pre-save hook hashes this before saving to MongoDB
      role: role || undefined, // Defaults to 'customer' as defined in User schema (Question 4)
    });

    await user.save();

    res.status(201).json({
      success: true,
      message: 'Customer registered successfully',
      user: {
        id: user._id,
        name: user.name,
        email: user.email,
        role: user.role,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Signup failed',
      error: error.message,
    });
  }
};

/**
 * Question 2 & Question 3:
 * Question 2: Verify the password when customer logs in using bcrypt.compare()
 * Question 3: Generate a JWT digital membership token on successful login
 */
const login = async (req, res) => {
  try {
    const { email, password } = req.body;

    // 1. Check if user exists
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({
        success: false,
        message: 'Invalid email or password',
      });
    }

    // 2. Question 2: Verify password using bcrypt (either user.comparePassword or bcrypt.compare)
    const isMatch = await user.comparePassword(password);
    // Or directly: const isMatch = await bcrypt.compare(password, user.password);

    if (!isMatch) {
      return res.status(400).json({
        success: false,
        message: 'Invalid email or password',
      });
    }

    // 3. Question 3: Issue JWT token (Digital Membership Pass)
    const payload = {
      id: user._id,
      email: user.email,
      role: user.role, // 'customer' or 'admin'
    };

    const token = jwt.sign(payload, JWT_SECRET, {
      expiresIn: '1d', // Valid for 1 day
    });

    res.status(200).json({
      success: true,
      message: 'Login successful! Here is your digital membership token.',
      token,
      user: {
        id: user._id,
        name: user.name,
        email: user.email,
        role: user.role,
      },
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Login error',
      error: error.message,
    });
  }
};

module.exports = {
  signup,
  login,
};

