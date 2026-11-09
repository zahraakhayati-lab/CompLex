const mongoose = require('mongoose');

const wordSchema = new mongoose.Schema({
    englishWord: {
        type: String,
        required: [true, 'وارد کردن لغت انگلیسی الزامی است'],
        unique: true,
        trim: true
    },
    definition: {
        type: String,
        required: [true, 'وارد کردن تعریف الزامی است']
    },
    example: {
        type: String
    },
    category: {
        type: String,
        required: [true, 'وارد کردن دسته‌بندی الزامی است']
    },
    difficulty: {
        type: String,
        enum: ['Beginner', 'Intermediate', 'Advanced'],
        default: 'Intermediate'
    }
}, { timestamps: true });

module.exports = mongoose.model('Word', wordSchema);