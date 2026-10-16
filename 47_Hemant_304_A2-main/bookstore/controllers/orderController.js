const Order = require('../models/Order');

/**
 * Supporting helper: Create an order so we have an order to cancel
 */
const createOrder = async (req, res) => {
  try {
    const { items, totalAmount } = req.body;

    const order = new Order({
      customer: req.user.id, // Logged in user from verifyToken middleware
      items,
      totalAmount,
      status: 'pending',
    });

    await order.save();

    res.status(201).json({
      success: true,
      message: 'Order placed successfully',
      order,
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to create order',
      error: error.message,
    });
  }
};

/**
 * Question 6: Cancelling an Order — Customer or Admin Only
 * Task: Write a DELETE /api/orders/:id handler that allows an order to be cancelled
 * only if the logged-in user is the customer who placed the order OR an admin.
 * Also checks that the order has not already shipped.
 */
const cancelOrder = async (req, res) => {
  try {
    const { id } = req.params;

    // 1. Find the order by its ID
    const order = await Order.findById(id);

    // If order is not found
    if (!order) {
      return res.status(404).json({
        success: false,
        message: 'Order not found',
      });
    }

    // 2. Business check: Customer can only cancel before it ships
    if (order.status === 'shipped' || order.status === 'delivered') {
      return res.status(400).json({
        success: false,
        message: `Order cannot be cancelled because it has already been ${order.status}`,
      });
    }

    if (order.status === 'cancelled') {
      return res.status(400).json({
        success: false,
        message: 'Order is already cancelled',
      });
    }

    // 3. Authorization check:
    // Only allowed if:
    // a) Logged-in user is an admin
    // OR
    // b) Logged-in user is the customer who placed the order
    const isOwner = order.customer.toString() === req.user.id.toString();
    const isAdminUser = req.user.role === 'admin';

    if (!isOwner && !isAdminUser) {
      return res.status(403).json({
        success: false,
        message: 'Forbidden: You are not authorized to cancel this order',
      });
    }

    // 4. Update the order status to 'cancelled' (or delete it with order.deleteOne())
    order.status = 'cancelled';
    await order.save();

    res.status(200).json({
      success: true,
      message: `Order #${order._id} has been cancelled successfully by ${
        isAdminUser && !isOwner ? 'an Admin' : 'the Customer'
      }.`,
      order,
    });
  } catch (error) {
    res.status(500).json({
      success: false,
      message: 'Failed to cancel order',
      error: error.message,
    });
  }
};

/**
 * Question 5 route handler: Admin-only inventory dashboard
 */
const getInventoryDashboard = async (req, res) => {
  // Simulated stock levels and sales data for the bookstore
  const inventoryData = [
    { bookId: 'B101', title: 'The Pragmatic Programmer', stock: 45, price: 850, totalSold: 120 },
    { bookId: 'B102', title: 'Clean Code', stock: 12, price: 799, totalSold: 250 },
    { bookId: 'B103', title: "You Don't Know JS Yet", stock: 0, price: 650, totalSold: 95 },
    { bookId: 'B104', title: 'Design Patterns', stock: 28, price: 1100, totalSold: 60 },
  ];

  res.status(200).json({
    success: true,
    message: 'Welcome to Admin Inventory Dashboard',
    data: inventoryData,
  });
};

module.exports = {
  createOrder,
  cancelOrder,
  getInventoryDashboard,
};

