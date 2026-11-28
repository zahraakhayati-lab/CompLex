
const express = require('express');
const router = express.Router();

const wordController = require('../controllers/wordController');

router.get('/dashboard/:userId', wordController.getDashboardStats);
router.get('/home', wordController.getHomeWords);

module.exports = router;
