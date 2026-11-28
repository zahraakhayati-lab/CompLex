const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const authRoutes = require('./routes/authRoutes');
const wordRoutes = require('./routes/wordRoutes');
const learningRoutes = require('./routes/learningRoutes');
const app = express();
require('dotenv').config();
app.use(cors());
app.use(express.json());
app.use('/api/auth', authRoutes);
app.use('/api/words', wordRoutes);
app.use('/api/learning', learningRoutes);
mongoose.connect(process.env.MONGO_URI)
    .then(() => console.log('Connected to MongoDB'))
    .catch((err) => console.error('Database connection error', err.message));
app.get('/', (req, res) => {
    res.status(200).json({ message: "Server and database are ready" });
});
const PORT = 3000;
app.listen(PORT, (err) => {
    if (err) {
        console.error("Server startup error:", err);
    } else {
        console.log(` Server is running on port ${PORT}`);
    }
});