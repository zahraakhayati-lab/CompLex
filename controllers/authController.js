const User = require('../models/User');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const JWT_SECRET = process.env.JWT_SECRET || 'complex_dev_secret_change_before_production';

const createToken = (user) => {
    return jwt.sign(
        { userId: user._id.toString() },
        JWT_SECRET,
        { expiresIn: '7d' }
    );
};

const getPublicUser = (user) => ({
    id: user._id.toString(),
    name: user.name,
    email: user.email,
    streak: user.streak,
    loginDays: user.loginDays,
    xp: user.xp,
    lives: user.lives,
    level: user.level,
    englishLevel: user.englishLevel,
    userType: user.userType,
    interests: user.interests,
    completedSteps: user.completedSteps,
    avatar: user.avatar
});

// ثبت‌نام
exports.register = async (req, res) => {
    try {
        let { name, email, password } = req.body;

        name = typeof name === 'string' ? name.trim() : '';
        email = typeof email === 'string' ? email.trim().toLowerCase() : '';

        if (!name || !email || !password) {
            return res.status(400).json({
                message: 'نام، ایمیل و رمز عبور الزامی هستند'
            });
        }

        if (password.length < 6) {
            return res.status(400).json({
                message: 'رمز عبور باید حداقل ۶ کاراکتر باشد'
            });
        }

        const existingUser = await User.findOne({ email });

        if (existingUser) {
            return res.status(409).json({
                message: 'این ایمیل قبلاً ثبت شده است'
            });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const newUser = await User.create({
            name,
            email,
            password: hashedPassword
        });

        const token = createToken(newUser);

        return res.status(201).json({
            message: 'کاربر با موفقیت ثبت‌نام شد',
            token,
            user: getPublicUser(newUser)
        });

    } catch (err) {
        if (err.code === 11000) {
            return res.status(409).json({
                message: 'این ایمیل قبلاً ثبت شده است'
            });
        }

        console.error('Register error:', err);

        return res.status(500).json({
            message: 'خطا در ثبت‌نام',
            error: err.message
        });
    }
};

// ورود
exports.login = async (req, res) => {
    try {
        const email = typeof req.body.email === 'string'
            ? req.body.email.trim().toLowerCase()
            : '';

        const { password } = req.body;

        if (!email || !password) {
            return res.status(400).json({
                message: 'ایمیل و رمز عبور الزامی هستند'
            });
        }

        const user = await User.findOne({ email });

        if (!user) {
            return res.status(401).json({
                message: 'ایمیل یا رمز عبور اشتباه است'
            });
        }

        const isMatch = await bcrypt.compare(password, user.password);

        if (!isMatch) {
            return res.status(401).json({
                message: 'ایمیل یا رمز عبور اشتباه است'
            });
        }

        // محاسبه استریک با مقایسه روزهای UTC
        const now = new Date();
        const today = new Date(Date.UTC(
            now.getUTCFullYear(),
            now.getUTCMonth(),
            now.getUTCDate()
        ));

        let currentStreak = user.streak || 0;

        if (user.lastLoginDate) {
            const previous = new Date(user.lastLoginDate);

            const previousDay = new Date(Date.UTC(
                previous.getUTCFullYear(),
                previous.getUTCMonth(),
                previous.getUTCDate()
            ));

            const diffDays = Math.floor(
                (today.getTime() - previousDay.getTime()) /
                (24 * 60 * 60 * 1000)
            );

            if (diffDays === 1) {
                currentStreak += 1;
            } else if (diffDays > 1) {
                currentStreak = 1;
            }
            // اگر همان روز دوباره وارد شود، استریک تغییر نمی‌کند.
        } else {
            currentStreak = 1;
        }

        const dayOfWeek = today.getUTCDay();
        const updatedLoginDays = user.loginDays || [];

        if (!updatedLoginDays.includes(dayOfWeek)) {
            updatedLoginDays.push(dayOfWeek);
        }

        user.streak = currentStreak;
        user.lastLoginDate = today;
        user.loginDays = updatedLoginDays;

        await user.save();

        const token = createToken(user);

        return res.status(200).json({
            message: 'ورود موفقیت‌آمیز بود',
            token,
            user: getPublicUser(user)
        });

    } catch (err) {
        console.error('Login error:', err);

        return res.status(500).json({
            message: 'خطا در ورود',
            error: err.message
        });
    }
};

// دریافت اطلاعات کاربر با شناسه
exports.getMe = async (req, res) => {
    try {
        const userId = req.user?.userId || req.params.userId;

        if (!userId) {
            return res.status(400).json({
                message: 'شناسه کاربر ارسال نشده است'
            });
        }

        const user = await User.findById(userId).select('-password');

        if (!user) {
            return res.status(404).json({
                message: 'کاربر پیدا نشد'
            });
        }

        return res.status(200).json({
            user: getPublicUser(user)
        });

    } catch (err) {
        console.error('Get user error:', err);

        return res.status(500).json({
            message: 'خطا در دریافت اطلاعات کاربر'
        });
    }
};

// تکمیل اطلاعات پروفایل و آنبوردینگ
exports.updateProfile = async (req, res) => {
    try {
        const { userId, name, englishLevel, userType, interests } = req.body;

        const id = req.user?.userId || userId;

        if (!id) {
            return res.status(400).json({
                message: 'شناسه کاربر الزامی است'
            });
        }

        const updates = {};

        if (typeof name === 'string' && name.trim()) {
            updates.name = name.trim();
        }

        if (['Beginner', 'Intermediate', 'Advanced'].includes(englishLevel)) {
            updates.englishLevel = englishLevel;
        }

        const allowedUserTypes = [
            'High School Student',
            'University Student',
            'Graduate Student',
            'Software Developer',
            'Researcher',
            'Working Professional',
            'Other'
        ];

        if (allowedUserTypes.includes(userType)) {
            updates.userType = userType;
        }

        if (Array.isArray(interests)) {
            updates.interests = interests;
        }

        const user = await User.findByIdAndUpdate(
            id,
            { $set: updates },
            { new: true, runValidators: true }
        );

        if (!user) {
            return res.status(404).json({
                message: 'کاربر پیدا نشد'
            });
        }

        return res.status(200).json({
            message: 'پروفایل با موفقیت به‌روزرسانی شد',
            user: getPublicUser(user)
        });

    } catch (err) {
        console.error('Update profile error:', err);

        return res.status(500).json({
            message: 'خطا در به‌روزرسانی پروفایل',
            error: err.message
        });
    }
};

// به‌روزرسانی XP و قلب‌ها
exports.updateStats = async (req, res) => {
    try {
        const { userId } = req.body;
        const id = req.user?.userId || userId;

        const xpToAdd = Number(req.body.xpToAdd || 0);
        const livesToRemove = Number(req.body.livesToRemove || 0);

        if (!id || !Number.isFinite(xpToAdd) ||
            !Number.isFinite(livesToRemove) ||
            xpToAdd < 0 || livesToRemove < 0) {
            return res.status(400).json({
                message: 'اطلاعات ارسالی معتبر نیست'
            });
        }

        const user = await User.findById(id);

        if (!user) {
            return res.status(404).json({
                message: 'کاربر پیدا نشد'
            });
        }

        user.xp += xpToAdd;
        user.level = Math.max(1, Math.floor(user.xp / 100) + 1);
        user.lives = Math.max(0, user.lives - livesToRemove);

        await user.save();

        return res.status(200).json({
            message: 'امتیاز و قلب‌ها به‌روزرسانی شدند',
            user: getPublicUser(user)
        });

    } catch (err) {
        console.error('Update stats error:', err);

        return res.status(500).json({
            message: 'خطا در به‌روزرسانی امتیازها'
        });
    }
};

// تغییر نام
exports.updateName = async (req, res) => {
    try {
        const { userId, name } = req.body;
        const id = req.user?.userId || userId;

        if (!id || typeof name !== 'string' || !name.trim()) {
            return res.status(400).json({
                message: 'نام معتبر وارد کنید'
            });
        }

        const user = await User.findByIdAndUpdate(
            id,
            { $set: { name: name.trim() } },
            { new: true, runValidators: true }
        );

        if (!user) {
            return res.status(404).json({
                message: 'کاربر پیدا نشد'
            });
        }

        return res.status(200).json({
            message: 'نام با موفقیت تغییر کرد',
            user: getPublicUser(user)
        });

    } catch (err) {
        console.error('Update name error:', err);

        return res.status(500).json({
            message: 'خطا در تغییر نام'
        });
    }
};

// تغییر آواتار
exports.updateAvatar = async (req, res) => {
    try {
        const { userId, avatar } = req.body;
        const id = req.user?.userId || userId;

        if (!id || typeof avatar !== 'string') {
            return res.status(400).json({
                message: 'اطلاعات آواتار معتبر نیست'
            });
        }

        const user = await User.findByIdAndUpdate(
            id,
            { $set: { avatar } },
            { new: true }
        );

        if (!user) {
            return res.status(404).json({
                message: 'کاربر پیدا نشد'
            });
        }

        return res.status(200).json({
            message: 'آواتار با موفقیت تغییر کرد',
            user: getPublicUser(user)
        });

    } catch (err) {
        console.error('Update avatar error:', err);

        return res.status(500).json({
            message: 'خطا در تغییر آواتار'
        });
    }
};

// تغییر رمز عبور
exports.changePassword = async (req, res) => {
    try {
        const { userId, currentPassword, newPassword } = req.body;
        const id = req.user?.userId || userId;

        if (!id || !currentPassword || !newPassword) {
            return res.status(400).json({
                message: 'تمام فیلدها الزامی هستند'
            });
        }

        if (newPassword.length < 6) {
            return res.status(400).json({
                message: 'رمز عبور جدید باید حداقل ۶ کاراکتر باشد'
            });
        }

        const user = await User.findById(id);

        if (!user) {
            return res.status(404).json({
                message: 'کاربر پیدا نشد'
            });
        }

        const isMatch = await bcrypt.compare(
            currentPassword,
            user.password
        );

        if (!isMatch) {
            return res.status(400).json({
                message: 'رمز عبور فعلی اشتباه است'
            });
        }

        user.password = await bcrypt.hash(newPassword, 10);
        await user.save();

        return res.status(200).json({
            message: 'رمز عبور با موفقیت تغییر کرد'
        });

    } catch (err) {
        console.error('Change password error:', err);

        return res.status(500).json({
            message: 'خطا در تغییر رمز عبور'
        });
    }
};