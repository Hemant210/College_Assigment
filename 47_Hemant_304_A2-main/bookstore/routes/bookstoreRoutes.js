const express = require('express');
const router = express.Router();

const { signup, login } = require('../controllers/authController');
const {
  createOrder,
  cancelOrder,
  getInventoryDashboard,
} = require('../controllers/orderController');
const { verifyToken, isAdmin } = require('../middleware/auth');

// ==========================================
// Question 2 & Question 3: Auth Routes
// ==========================================
// POST /api/auth/signup - Hashes password using bcrypt and stores in MongoDB (Q2)
router.post('/auth/signup', signup);

// POST /api/auth/login - Verifies password with bcrypt (Q2) and issues JWT token (Q3)
router.post('/auth/login', login);

// ==========================================
// Question 3 & Question 6: Order Routes
// ==========================================
// POST /api/orders - Protected route (requires token verification)
router.post('/orders', verifyToken, createOrder);

// DELETE /api/orders/:id - Cancel order (Customer who placed it or Admin only) (Q6)
router.delete('/orders/:id', verifyToken, cancelOrder);

// ==========================================
// Question 5: Admin Inventory Route
// ==========================================
// GET /api/admin/inventory - Accessible only by Admins (403 Forbidden for regular customers) (Q5)
router.get('/admin/inventory', verifyToken, isAdmin, getInventoryDashboard);

module.exports = router;

