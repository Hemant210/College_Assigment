const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const routes = require('./routes/bookstoreRoutes');

dotenv.config();

const app = express();
app.use(express.json());

// Mount the bookstore routes under /api
app.use('/api', routes);

// Root route
app.get('/', (req, res) => {
  res.send({
    message: 'Bookstore API is running',
    endpoints: {
      signup: 'POST /api/auth/signup',
      login: 'POST /api/auth/login',
      createOrder: 'POST /api/orders (Requires Bearer token)',
      cancelOrder: 'DELETE /api/orders/:id (Customer owner or Admin only)',
      adminInventory: 'GET /api/admin/inventory (Admin only)',
    },
  });
});

const PORT = process.env.PORT || 5000;
const MONGO_URI =
  process.env.MONGO_URI || 'mongodb://127.0.0.1:27017/bookstore_db';

// Connect to MongoDB and start the server
if (process.env.NODE_ENV !== 'test') {
  mongoose
    .connect(MONGO_URI)
    .then(() => {
      console.log('Connected to MongoDB successfully');
      app.listen(PORT, () => {
        console.log(`Server running on http://localhost:${PORT}`);
      });
    })
    .catch((err) => {
      console.error('Failed to connect to MongoDB:', err.message);
    });
}

module.exports = app;

