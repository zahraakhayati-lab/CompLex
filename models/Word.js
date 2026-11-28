const mongoose = require('mongoose');

const wordSchema = new mongoose.Schema({
    englishWord: {
        type: String,
        required: [true, 'English word is required'],
        unique: true,
        trim: true
    },
    definition: {
        type: String,
        required: [true, 'Definition is required']
    },
    example: {
        type: String
    },
    category: {
        type: String,
        required: [true, 'Category is required']
    },
    difficulty: {
        type: String,
        enum: ['Beginner', 'Intermediate', 'Advanced'],
        default: 'Intermediate'
    }
}, { timestamps: true });

module.exports = mongoose.model('Word', wordSchema);