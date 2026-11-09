const User = require('../models/User');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const JWT_SECRET = 'super_secret_key_123';

// ۱. ثبت‌نام
exports.register = async (req, res) => {
    try {
        console.log("دیتای دریافتی از Postman:", req.body);
        const { name, email, password } = req.body;
        
        const existingUser = await User.findOne({ email });
        if (existingUser) {
            return res.status(400).json({ message: 'این ایمیل قبلاً ثبت شده است' });
        }

        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);

        const newUser = new User({
            name,
            email,
            password: hashedPassword
        });

        await newUser.save();
        res.status(201).json({ message: 'کاربر با موفقیت ثبت‌نام شد' });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

// ۲. ورود
exports.login = async (req, res) => {
    try {
        const { email, password } = req.body;
        const user = await User.findOne({ email });
        
        if (!user) {
            return res.status(400).json({ message: 'ایمیل یا رمز عبور اشتباه است' });
        }

        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(400).json({ message: 'ایمیل یا رمز عبور اشتباه است' });
        }

        // ====== محاسبه استریک (اصلاح شده) ======
        const today = new Date();
        today.setHours(0, 0, 0, 0); // صفر کردن ساعت برای مقایسه دقیق روزها

        let currentStreak = user.streak || 0;
        
        // اصلاح شد: استفاده از Date به جای DataTransfer
        let lastLogin = user.lastLoginDate ? new Date(user.lastLoginDate) : null;

        if (lastLogin) {
            lastLogin.setHours(0, 0, 0, 0); // صفر کردن ساعت تاریخ قبلی
            
            // محاسبه اختلاف روزها
            const diffTime = Math.abs(today - lastLogin);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

            if (diffDays === 1) {
                currentStreak += 1; // اگر دیروز لاگین کرده بود، استریک زیاد میشود
            } else if (diffDays > 1) {
                currentStreak = 1; // اگر بیشتر از یک روز نیامده بود، استریک ریست میشود
            }
        } else {
            currentStreak = 1; // اولین بار
        }

        // ثبت روزهای هفته
        const dayOfWeek = today.getDay();
        let updatedLoginDays = user.loginDays || [];
        if (!updatedLoginDays.includes(dayOfWeek)) {
            updatedLoginDays.push(dayOfWeek);
        }

        // ذخیره تغییرات در دیتابیس
        user.streak = currentStreak;
        user.lastLoginDate = today; // اصلاح شد
        user.loginDays = updatedLoginDays;
        await user.save();
        // =======================================

        const token = jwt.sign({ userId: user._id }, JWT_SECRET, { expiresIn: '7d' });

        res.status(200).json({
            message: 'ورود موفقیت‌آمیز بود',
            token,
            user: {
                id: user._id,
                name: user.name,
                email: user.email,
                streak: user.streak,
                loginDays: user.loginDays, // اصلاح شد (حرف و اضافه پاک شد)
                xp: user.xp,
                lives: user.lives
            }
        });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

// ۳. آپدیت پروفایل (آنبوردینگ)
exports.updateProfile = async (req, res) => {
    try {
        const { userId, name, englishLevel, userType, interests } = req.body;
        
        const updatedUser = await User.findByIdAndUpdate(
            userId,
            { name, englishLevel, userType, interests },
            { new: true }
        );
        
        if (!updatedUser) {
            return res.status(404).json({ message: 'کاربر پیدا نشد' });
        }
        
        // اصلاح شد: updatedUser
        res.status(200).json({ message: 'پروفایل با موفقیت آپدیت شد', user: updatedUser });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

// ۴. آپدیت XP و Lives
exports.updateStats = async (req, res) => {
    try {
        const { userId, xpToAdd, livesToRemove } = req.body;
        
        const user = await User.findById(userId);
        if (!user) return res.status(404).json({ message: 'کاربر پیدا نشد' });

        if (xpToAdd) user.xp += xpToAdd;
        if (livesToRemove) {
            user.lives = Math.max(0, user.lives - livesToRemove);
        }

        await user.save();
        res.status(200).json({ xp: user.xp, lives: user.lives });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};
// ۵. تغییر نام کاربر (صفحه What's your name)
exports.updateName = async (req, res) => {
    try {
        const { userId, name } = req.body;
        const user = await User.findByIdAndUpdate(
            userId,
            { name: name },
            { new: true }
        );
        if (!user) return res.status(404).json({ message: 'کاربر پیدا نشد' });
        res.status(200).json({ message: 'نام با موفقیت تغییر کرد', name: user.name });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

// ۶. تغییر رمز عبور (صفحه Change Password)
exports.changePassword = async (req, res) => {
    try {
        const { userId, currentPassword, newPassword } = req.body;
        const user = await User.findById(userId);
        if (!user) return res.status(404).json({ message: 'کاربر پیدا نشد' });

        // چک کردن رمز فعلی
        const isMatch = await bcrypt.compare(currentPassword, user.password);
        if (!isMatch) {
            return res.status(400).json({ message: 'رمز عبور فعلی اشتباه است' });
        }

        // هش کردن رمز جدید و ذخیره
        const salt = await bcrypt.genSalt(10);
        user.password = await bcrypt.hash(newPassword, salt);
        await user.save();

        res.status(200).json({ message: 'رمز عبور با موفقیت تغییر کرد' });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};