const express = require('express');
const router = express.Router();
const learningController = require('../controllers/learningController');

router.get('/path', learningController.getLearningPath);
router.post('/complete-step', learningController.completeStep);
router.get('/quiz', learningController.generateQuiz);
router.get('/review/:pathId', learningController.getReviewData);
router.get('/reading-quiz/:pathId', learningController.getReadingQuiz);

module.exports = router;