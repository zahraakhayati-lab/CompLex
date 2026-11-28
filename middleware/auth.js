const jwt = require('jsonwebtoken');

const JWT_SECRET = process.env.JWT_SECRET || 'complex_dev_secret_change_before_production';

const authMiddleware = (req, res, next) => {
    try {
        if (!JWT_SECRET) {
            console.error('JWT_SECRET is not configured');

            return res.status(500).json({
                message: 'Server authentication is not configured.'
            });
        }

        const authHeader = req.headers.authorization;

        if (!authHeader || !authHeader.startsWith('Bearer ')) {
            return res.status(401).json({
                message: 'Please log in to access this resource.'
            });
        }

        const token = authHeader.split(' ')[1];

        if (!token) {
            return res.status(401).json({
                message: 'Authentication token is missing.'
            });
        }

        const decoded = jwt.verify(token, JWT_SECRET);

        if (!decoded.userId) {
            return res.status(401).json({
                message: 'Invalid authentication token.'
            });
        }

        req.user = {
            userId: decoded.userId
        };

        next();

    } catch (error) {
        if (error.name === 'TokenExpiredError') {
            return res.status(401).json({
                message: 'Your session has expired. Please log in again.'
            });
        }

        return res.status(401).json({
            message: 'Invalid authentication token. Please log in again.'
        });
    }
};

module.exports = authMiddleware;