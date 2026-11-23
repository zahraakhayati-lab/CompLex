const mongoose = require('mongoose');

const learningPathSchema = new mongoose.Schema({
    title: { type: String, required: true },
    description: { type: String },
    steps: [{
        title: { type: String, required: true },
        type: { type: String, enum: ['Reading', 'Practice', 'Review', 'Quiz'] },
        xpReward: { type: Number, default: 30 },
        content: { type: String },
        quiz: [{
            question: String,
            options: [String],
            correctAnswer: String
        }]
    }],
    words: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Word'
    }],
    category: { type: String }
}, { timestamps: true });

module.exports = mongoose.model('LearningPath', learningPathSchema);