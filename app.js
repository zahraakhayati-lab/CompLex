const express = require('express');
const mongoose =require('mongoose');
const cors = require('cors');
const authRoutes =require('./routes/authRoutes');
const wordRoutes = require('./routes/wordRoutes');
const learningRoutes = require('./routes/learningRoutes');
const app = express();
require('dotenv').config();
app.use(cors());
app.use(express.json());
app.use('/api/auth',authRoutes);
app.use('/api/words', wordRoutes);
app.use('/api/learning', learningRoutes);
mongoose.connect(process.env.MONGO_URI)
    .then(()=>console.log('به MongoDB متصل شد'))
    .catch((err)=> console.error('خطا در اتصال به دیتابیس',err.message));
app.get('/', (req, res) => {
    res.status(200).json({ message: "سرور و دیتابیس اماده هستند" });
});
const PORT = 3000;
app.listen(PORT, (err) => {
    if (err) {
        console.error("خطا در راه‌اندازی سرور:", err);
    } else {
        console.log(` Server is running on port ${PORT}`);
    }
});