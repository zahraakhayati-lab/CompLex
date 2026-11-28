const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    name: {
        type: String,
        required: [true, 'وارد کردن نام الزامی است'],
        trim: true
    },

    speakingStarted: {
        type: Boolean,
        default: false
    },

    email: {
        type: String,
        required: [true, 'وارد کردن ایمیل الزامی است'],
        unique: true,
        lowercase: true,
        trim: true
    },

    password: {
        type: String,
        required: [true, 'وارد کردن رمز عبور الزامی است'],
        minlength: 6
    },

    englishLevel: {
        type: String,
        enum: ['Beginner', 'Intermediate', 'Advanced'],
        default: 'Beginner'
    },

    userType: {
        type: String,
        enum: [
            'High School Student',
            'University Student',
            'Graduate Student',
            'Software Developer',
            'Researcher',
            'Working Professional',
            'Other'
        ],
        default: 'University Student'
    },

    interests: [{
        type: String,
        enum: [
            'Programming',
            'Algorithms',
            'AI & Machine Learning',
            'Data Structures',
            'Computer Networks',
            'Operating Systems',
            'Databases',
            'Cybersecurity',
            'Cloud Computing',
            'Web Development',
            'Software Engineering',
            'Computer Architecture'
        ]
    }],

    savedWords: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Word'
    }],

    // سطح فعلی کاربر
    level: {
        type: Number,
        default: 1,
        min: 1
    },

    // امتیاز تجربه
    xp: {
        type: Number,
        default: 0,
        min: 0
    },

    // تعداد قلب‌ها
    lives: {
        type: Number,
        default: 5,
        min: 0,
        max: 5
    },

    // روزهای متوالی فعالیت
    streak: {
        type: Number,
        default: 0,
        min: 0
    },

    lastLoginDate: {
        type: Date,
        default: null
    },

    loginDays: [{
        type: Number,
        min: 0,
        max: 6
    }],

    // شناسه درس‌هایی که کاربر کامل کرده است
    completedSteps: [{
        type: String
    }],

    // آواتار انتخاب‌شده
    avatar: {
        type: String,
        default: ''
    },

    resetPasswordCode: {
        type: String,
        default: null
    },

    resetPasswordExpires: {
        type: Date,
        default: null
    }

}, {
    timestamps: true
});

module.exports = mongoose.model('User', userSchema);