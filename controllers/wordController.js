
const User = require('../models/User');
const Word = require('../models/Word');

// Get dashboard statistics
exports.getDashboardStats = async (req, res) => {
    try {
        const user = await User.findById(req.params.userId);

        if (!user) {
            return res.status(404).json({
                message: "User not found"
            });
        }

        return res.status(200).json({
            level: user.level ?? 1,
            streak: user.streak ?? 0,
            xp: user.xp ?? 0,
            lives: user.lives ?? 5,
            savedWordsCount: user.savedWords
                ? user.savedWords.length
                : 0
        });

    } catch (error) {
        console.error("Dashboard error:", error);

        return res.status(500).json({
            message: "Could not load dashboard statistics"
        });
    }
};

// Get a random word for Home

exports.getHomeWords = async (req, res) => {
    try {
        const words = await Word.aggregate([
            { $sample: { size: 1 } }
        ]);

        if (!words || words.length === 0) {
            return res.status(404).json({
                message: "No words found"
            });
        }

        return res.status(200).json(words[0]);

    } catch (error) {
        console.error("Home word error:", error);

        return res.status(500).json({
            message: "Could not load home word"
        });
    }
};
