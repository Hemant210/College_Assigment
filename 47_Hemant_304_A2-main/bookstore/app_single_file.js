/**
 * Complete Bookstore Application
 * Covering Questions 2, 3, 4, 5, and 6 in a single unified script.
 */

const express = require('express');
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const app = express();
app.use(express.json());

const JWT_SECRET = process.env.JWT_SECRET || 'bookstore_secret_pass_key_123';

// =============================================================================
// QUESTION 4: Defining Customer and Admin Roles (Mongoose User Schema)
// =============================================================================
const userSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    trim: true,
  },
  email: {
    type: String,
    required: true,
    unique: true,
    lowercase: true,
  },
  password: {
    type: String,
    required: true,
  },
  role: {
    type: String,
    enum: ['customer', 'admin'],
    default: 'customer', // Defaults to 'customer' for all new signups
  },
});

// =============================================================================
// QUESTION 2: Password Hashing with Bcrypt & Password Verification Logic
// =============================================================================

// Mongoose pre-save hook to hash password before saving to MongoDB
userSchema.pre('save', async function () {
  if (!this.isModified('password')) return;
  const salt = await bcrypt.genSalt(10);
  this.password = await bcrypt.hash(this.password, salt);
});

// Method to verify password during login
userSchema.methods.comparePassword = async function (enteredPassword) {
  return await bcrypt.compare(enteredPassword, this.password);
};

const User = mongoose.model('User', userSchema);

// Order Schema (Needed for Question 6)
const orderSchema = new mongoose.Schema({
  customer: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true,
  },
  books: [
    {
      title: String,
      quantity: Number,
      price: Number,
    },
  ],
  totalAmount: Number,
  status: {
    type: String,
    enum: ['pending', 'shipped', 'delivered', 'cancelled'],
    default: 'pending',
  },
});

const Order = mongoose.model('Order', orderSchema);

// =============================================================================
// QUESTION 3: Token Verification Middleware (Digital Membership Pass)
// =============================================================================
const verifyToken = (req, res, next) => {
  const authHeader = req.headers.authorization;

  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({
      message: 'Access denied: No token provided or invalid format',
    });
  }

  const token = authHeader.split(' ')[1];

  try {
    const decoded = jwt.verify(token, JWT_SECRET);
    req.user = decoded; // Contains { id, email, role }
    next();
  } catch (error) {
    return res.status(401).json({
      message: 'Invalid or expired token',
    });
  }
};

// =============================================================================
// QUESTION 5: Authorization Middleware for Admin-Only Access
// =============================================================================
const isAdmin = (req, res, next) => {
  if (!req.user || req.user.role !== 'admin') {
    return res.status(403).json({
      message: '403 Forbidden: Access restricted to administrators only',
    });
  }
  next();
};

// =============================================================================
// ROUTES & CONTROLLERS
// =============================================================================

// QUESTION 2: Secure Signup
app.post('/api/auth/signup', async (req, res) => {
  try {
    const { name, email, password, role } = req.body;

    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({ message: 'User already exists' });
    }

    const user = new User({
      name,
      email,
      password, // Password will be hashed by pre-save hook
      role: role || undefined, // Will default to 'customer' if not provided
    });

    await user.save();

    res.status(201).json({
      message: 'Customer registered successfully',
      user: { id: user._id, name: user.name, email: user.email, role: user.role },
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// QUESTION 2 & QUESTION 3: Login (Verify Password + Issue JWT Token)
app.post('/api/auth/login', async (req, res) => {
  try {
    const { email, password } = req.body;

    // Check user exists
    const user = await User.findOne({ email });
    if (!user) {
      return res.status(400).json({ message: 'Invalid credentials' });
    }

    // Question 2: Verify password using bcrypt
    const isMatch = await user.comparePassword(password);
    if (!isMatch) {
      return res.status(400).json({ message: 'Invalid credentials' });
    }

    // Question 3: Generate JWT token (digital membership pass)
    const token = jwt.sign(
      { id: user._id, email: user.email, role: user.role },
      JWT_SECRET,
      { expiresIn: '1d' }
    );

    res.status(200).json({
      message: 'Login successful',
      token,
      user: { id: user._id, name: user.name, email: user.email, role: user.role },
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Helper: Place Order (Protected Route)
app.post('/api/orders', verifyToken, async (req, res) => {
  try {
    const { books, totalAmount } = req.body;
    const order = new Order({
      customer: req.user.id,
      books,
      totalAmount,
      status: 'pending',
    });
    await order.save();
    res.status(201).json({ message: 'Order placed successfully', order });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// QUESTION 5: Admin-Only Inventory Dashboard Route
app.get('/api/admin/inventory', verifyToken, isAdmin, (req, res) => {
  // Returns stock levels and sales data
  res.status(200).json({
    message: 'Inventory & Sales Data',
    inventory: [
      { bookId: 'BK101', title: 'Node.js in Action', stock: 50, price: 599, totalSales: 130 },
      { bookId: 'BK102', title: 'MongoDB: The Definitive Guide', stock: 15, price: 749, totalSales: 85 },
      { bookId: 'BK103', title: 'Clean Architecture', stock: 3, price: 899, totalSales: 210 },
    ],
  });
});

// QUESTION 6: Cancelling an Order — Customer or Admin Only
app.delete('/api/orders/:id', verifyToken, async (req, res) => {
  try {
    const order = await Order.findById(req.params.id);

    // 1. Order existence check
    if (!order) {
      return res.status(404).json({ message: 'Order not found' });
    }

    // 2. Shipping status check: Can only be cancelled before it ships
    if (order.status === 'shipped' || order.status === 'delivered') {
      return res.status(400).json({
        message: `Cannot cancel order. It has already been ${order.status}.`,
      });
    }

    if (order.status === 'cancelled') {
      return res.status(400).json({ message: 'Order is already cancelled' });
    }

    // 3. Authorization check: Customer who placed the order OR Admin
    const isOwner = order.customer.toString() === req.user.id.toString();
    const isAdminUser = req.user.role === 'admin';

    if (!isOwner && !isAdminUser) {
      return res.status(403).json({
        message: '403 Forbidden: You do not have permission to cancel this order',
      });
    }

    // 4. Cancel the order
    order.status = 'cancelled';
    await order.save();

    res.status(200).json({
      message: `Order #${order._id} cancelled successfully by ${isAdminUser && !isOwner ? 'Admin' : 'Customer'}`,
      order,
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Bookstore server listening on port ${PORT}`);
});

