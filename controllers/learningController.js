const User = require('../models/User');
const Word = require('../models/Word');
const LearningPath = require('../models/LearningPath');

exports.getLearningPath = async (req, res) => {
    try {
        const paths = await LearningPath.find().populate('words');
        res.status(200).json(paths);
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

exports.completeStep = async (req, res) => {
    try {
        const { userId, stepId, xpReward, isCorrect } = req.body;
        const user = await User.findById(userId);
        if (!user) return res.status(404).json({ message: 'کاربر پیدا نشد' });

        user.xp += xpReward;
        while (user.xp >= 100) {
            user.level += 1;
            user.xp -= 100;
        }

        if (isCorrect === false) {
            user.lives = Math.max(0, user.lives - 1);
        }

        if (!user.completedSteps.includes(stepId)) {
            user.completedSteps.push(stepId);
        }

        await user.save();
        res.status(200).json({ message: 'استپ با موفقیت تکمیل شد', xp: user.xp, level: user.level, lives: user.lives });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

exports.generateQuiz = async (req, res) => {
    try {
        const randomWord = await Word.aggregate([{ $sample: { size: 1 } }]);
        const correctWord = randomWord[0];

        const wrongOptions = await Word.aggregate([
            { $match: { _id: { $ne: correctWord._id } } },
            { $sample: { size: 3 } }
        ]);

        let options = [correctWord.englishWord, ...wrongOptions.map(w => w.englishWord)].sort(() => Math.random() - 0.5);

        res.status(200).json({
            question: `Which word means... "${correctWord.definition}"`,
            correctAnswer: correctWord.englishWord,
            options: options
        });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

exports.getReviewData = async (req, res) => {
    try {
        const { pathId } = req.params;
        const path = await LearningPath.findById(pathId).populate('words');
        if (!path) return res.status(404).json({ message: 'مسیر پیدا نشد' });

        const words = path.words.map(w => ({ id: w._id, text: w.englishWord }));
        const meanings = path.words.map(w => ({ id: w._id, text: w.definition }));

        res.status(200).json({
            words: words.sort(() => Math.random() - 0.5),
            meanings: meanings.sort(() => Math.random() - 0.5)
        });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};

exports.getReadingQuiz = async (req, res) => {
    try {
        const { pathId } = req.params;
        const path = await LearningPath.findById(pathId);
        if (!path) return res.status(404).json({ message: 'مسیر پیدا نشد' });

        const quizStep = path.steps.find(step => step.type === 'ReadingQuiz');
        if (!quizStep) return res.status(404).json({ message: 'بخش Reading Quiz در این مسیر وجود ندارد' });

        res.status(200).json({
            title: quizStep.title,
            xpReward: quizStep.xpReward,
            questions: quizStep.quiz
        });
    } catch (err) {
        res.status(500).json({ message: 'خطا در سرور', error: err.message });
    }
};