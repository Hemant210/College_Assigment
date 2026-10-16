const jwt = require('jsonwebtoken');

const JWT_SECRET = process.env.JWT_SECRET || 'bookstore_super_secret_jwt_key_123';

/**
 * Question 3: Middleware to verify JWT token before allowing access to protected routes
 */
const verifyToken = (req, res, next) => {
  const authHeader = req.headers.authorization;

  // Check if Authorization header exists and follows "Bearer <token>" format
  if (!authHeader || !authHeader.startsWith('Bearer ')) {
    return res.status(401).json({
      success: false,
      message: 'Access Denied: No token provided or invalid format. Please log in.',
    });
  }

  const token = authHeader.split(' ')[1];

  try {
    // Verify token validity and extract payload
    const decoded = jwt.verify(token, JWT_SECRET);

    // Attach decoded user info ({ id, role, email, ... }) to the request object
    req.user = decoded;
    next();
  } catch (error) {
    return res.status(401).json({
      success: false,
      message: 'Invalid or expired token. Please log in again.',
      error: error.message,
    });
  }
};

/**
 * Question 5: Authorization middleware to restrict routes to 'admin' users only
 */
const isAdmin = (req, res, next) => {
  // Ensure user is authenticated first
  if (!req.user) {
    return res.status(401).json({
      success: false,
      message: 'Authentication required',
    });
  }

  // Check if role is admin
  if (req.user.role !== 'admin') {
    return res.status(403).json({
      success: false,
      message: 'Access Forbidden: Admin access required',
    });
  }

  next();
};

module.exports = {
  verifyToken,
  isAdmin,
  JWT_SECRET,
};

