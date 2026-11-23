const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const authRoutes = require('./routes/authRoutes');
const wordRoutes = require('./routes/wordRoutes');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());

// Log every incoming request to help diagnose API errors.
app.use((req, res, next) => {
    console.log(`[REQUEST] ${req.method} ${req.originalUrl}`);
    next();
});

// Basic health check
app.get('/', (req, res) => {
    res.status(200).json({
        success: true,
        message: 'CompLex API server is running'
    });
});

// Authentication route health check
app.get('/api/auth/test', (req, res) => {
    res.status(200).json({
        success: true,
        message: 'Authentication routes are reachable'
    });
});

// API routes
app.use('/api/auth', authRoutes);
app.use('/api/words', wordRoutes);

// Return a useful response for unknown routes.
app.use((req, res) => {
    console.log(`[404] Route not found: ${req.method} ${req.originalUrl}`);
    res.status(404).json({
        success: false,
        message: 'Route not found',
        method: req.method,
        path: req.originalUrl
    });
});

// Global error handler
app.use((err, req, res, next) => {
    console.error('[SERVER ERROR]', err);
    res.status(500).json({
        success: false,
        message: 'Internal server error'
    });
});

// Connect to MongoDB when a connection string is configured.
if (process.env.MONGO_URI) {
    mongoose.connect(process.env.MONGO_URI)
        .then(() => console.log('[DATABASE] Connected to MongoDB'))
        .catch((err) => console.error('[DATABASE ERROR]', err.message));
} else {
    console.warn('[DATABASE] MONGO_URI is not configured. Database features will not work.');
}

// Listen on all network interfaces so the Android Emulator can reach the host.
app.listen(PORT, '0.0.0.0', () => {
    console.log('================================');
    console.log('CompLex API Server Started');
    console.log(`Port: ${PORT}`);
    console.log(`Local URL: http://localhost:${PORT}`);
    console.log('================================');
});
