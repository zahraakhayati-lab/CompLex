const express = require('express');
const router = express.Router();

const authController = require('../controllers/authController');
const authMiddleware = require('../middleware/auth');

// مسیرهای عمومی
router.post('/register', authController.register);
router.post('/login', authController.login);

// مسیرهای نیازمند ورود
router.get('/me', authMiddleware, authController.getMe);

router.put(
    '/update-profile',
    authMiddleware,
    authController.updateProfile
);

router.put(
    '/update-stats',
    authMiddleware,
    authController.updateStats
);

router.put(
    '/update-name',
    authMiddleware,
    authController.updateName
);

router.put(
    '/update-avatar',
    authMiddleware,
    authController.updateAvatar
);

router.put(
    '/change-password',
    authMiddleware,
    authController.changePassword
);

module.exports = router;