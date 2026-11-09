package com.example.complex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.complex.ui.AllSetScreen
import com.example.complex.ui.EnglishLevel
import com.example.complex.ui.EnglishLevelScreen
import com.example.complex.ui.ForgotPasswordScreen
import com.example.complex.ui.HomeDashboardScreen
import com.example.complex.ui.LevelScreen
import com.example.complex.ui.LoginScreen
import com.example.complex.ui.LogoutConfirmScreen
import com.example.complex.ui.ProfileDashboardScreen
import com.example.complex.ui.SignUpScreen
import com.example.complex.ui.TopicInterestScreen
import com.example.complex.ui.UserLevel
import com.example.complex.ui.UserNameScreen
import com.example.complex.ui.VerifyCodeScreen
import com.example.complex.ui.WelcomeScreen

private enum class Screen {
    Welcome, Login, ForgotPassword, VerifyCode, SignUp, Name, Level, EnglishLevel, Topic,
    AllSet, Home, Profile, LogoutConfirm
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme { // اگر تم خودتان را دارید (مثلا CompLexTheme) به‌جای آن بگذارید
                var screen by rememberSaveable { mutableStateOf(Screen.Welcome) }

                // اطلاعات کاربر (به‌صورت ساده در حافظه؛ بعداً به ViewModel/دیتابیس منتقل کنید)
                var userEmail by rememberSaveable { mutableStateOf("") }
                var recoveryEmail by rememberSaveable { mutableStateOf("") } // ایمیل صفحه‌ی بازیابی
                var firstName by rememberSaveable { mutableStateOf("") }
                var lastName by rememberSaveable { mutableStateOf("") }
                var userLevelName by rememberSaveable { mutableStateOf<String?>(null) }
                var englishLevelName by rememberSaveable { mutableStateOf<String?>(null) }
                var avatarIndex by rememberSaveable { mutableStateOf<Int?>(null) }

                // وقتی عکس‌های آواتار را در res/drawable گذاشتید، این لیست را فعال کنید:
                // val avatarResIds = listOf(
                //     R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3,
                //     R.drawable.avatar_4, R.drawable.avatar_5, R.drawable.avatar_6
                // )
                val avatarResIds = emptyList<Int>()

                // دکمه‌ی Back گوشی (در Home فعال نیست و برنامه بسته می‌شود)
                BackHandler(enabled = screen != Screen.Welcome && screen != Screen.Home) {
                    screen = when (screen) {
                        Screen.LogoutConfirm -> Screen.Profile
                        Screen.Profile -> Screen.Home
                        Screen.AllSet -> Screen.Topic
                        Screen.Topic -> Screen.EnglishLevel
                        Screen.EnglishLevel -> Screen.Level
                        Screen.Level -> Screen.Name
                        Screen.Name -> Screen.SignUp
                        Screen.SignUp -> Screen.Login
                        Screen.VerifyCode -> Screen.ForgotPassword
                        Screen.ForgotPassword -> Screen.Login
                        else -> Screen.Welcome
                    }
                }

                when (screen) {
                    // 1) صفحه‌ی اول
                    Screen.Welcome -> WelcomeScreen(
                        onGetStarted = { screen = Screen.Login }
                        // logo = painterResource(R.drawable.complex_logo),
                        // illustration = painterResource(R.drawable.welcome_illustration),
                    )

                    // 2) ورود
                    Screen.Login -> LoginScreen(
                        onBack = { screen = Screen.Welcome },
                        onLoginSuccess = { screen = Screen.Level },
                        onNavigateToRegister = { screen = Screen.SignUp },
                        onForgotPassword = { screen = Screen.ForgotPassword }
                    )

                    // 2.1) بازیابی رمز: وارد کردن ایمیل
                    Screen.ForgotPassword -> ForgotPasswordScreen(
                        onBack = { screen = Screen.Login },
                        onNext = { email ->
                            recoveryEmail = email
                            // TODO: ارسال کد یک‌بار مصرف به ایمیل از طریق سرور
                            screen = Screen.VerifyCode
                        }
                    )

                    // 2.2) بازیابی رمز: وارد کردن کد
                    Screen.VerifyCode -> VerifyCodeScreen(
                        onBack = { screen = Screen.ForgotPassword },
                        onOk = { code ->
                            // TODO: بررسی کد با سرور (برای recoveryEmail)
                            // اگر درست بود: رفتن به صفحه‌ی تعیین رمز جدید
                        }
                    )

                    // 3) ثبت‌نام
                    Screen.SignUp -> SignUpScreen(
                        onBack = { screen = Screen.Login },
                        onCreateAccount = { email, password ->
                            // TODO: اتصال به ViewModel؛ بعد از ثبت‌نام موفق:
                            userEmail = email
                            screen = Screen.Name
                        },
                        onNavigateToLogin = { screen = Screen.Login }
                    )

                    // 4) نام
                    Screen.Name -> UserNameScreen(
                        onBack = { screen = Screen.SignUp },
                        onContinue = { first, last ->
                            firstName = first
                            lastName = last
                            screen = Screen.Level
                        }
                    )

                    // 5) سطح فعلی (دانش‌آموز، دانشجو، ...)
                    Screen.Level -> LevelScreen(
                        onBack = { screen = Screen.Name },
                        onContinue = { level ->
                            userLevelName = level.name
                            screen = Screen.EnglishLevel
                        }
                    )

                    // 6) سطح انگلیسی
                    Screen.EnglishLevel -> EnglishLevelScreen(
                        onBack = { screen = Screen.Level },
                        onContinue = { englishLevel ->
                            englishLevelName = englishLevel.name
                            screen = Screen.Topic
                        }
                    )

                    // 7) موضوع‌ها (چندانتخابی)
                    Screen.Topic -> TopicInterestScreen(
                        onBack = { screen = Screen.EnglishLevel },
                        onContinue = { topics ->
                            // TODO: ذخیره‌ی موضوع‌ها
                            screen = Screen.AllSet
                        }
                    )

                    // 8) You are all set!
                    Screen.AllSet -> AllSetScreen(
                        userLevel = userLevelName?.let { UserLevel.valueOf(it) },
                        englishLevel = englishLevelName?.let { EnglishLevel.valueOf(it) },
                        onBack = { screen = Screen.Topic },
                        onContinue = { screen = Screen.Home }
                        // illustration = painterResource(R.drawable.all_set_illustration),
                    )

                    // 9) صفحه‌ی اصلی
                    Screen.Home -> HomeDashboardScreen(
                        onMenuClick = { screen = Screen.Profile }, // آیکن چهارخانه ← Dashboard
                        onTabSelected = { tab ->
                            // TODO: رفتن به صفحه‌های Learning / Dictionary / Competition
                        }
                    )

                    // 10) Dashboard (پروفایل)
                    Screen.Profile -> ProfileDashboardScreen(
                        // اگر کاربر از Login آمده و هنوز نام/ایمیلی ثبت نشده، نمونه‌ی طرح نمایش داده می‌شود
                        fullName = "$firstName $lastName".trim().ifBlank { "Lily Woods" },
                        email = userEmail.ifBlank { "Lily@test.com" },
                        userLevelTitle = userLevelName?.let { UserLevel.valueOf(it).title },
                        selectedAvatar = avatarIndex,
                        avatarResIds = avatarResIds,
                        onAvatarSelected = { avatarIndex = it },
                        onBack = { screen = Screen.Home },
                        onLogOut = { screen = Screen.LogoutConfirm }
                    )

                    // 11) تأیید خروج
                    Screen.LogoutConfirm -> LogoutConfirmScreen(
                        onBack = { screen = Screen.Profile },
                        onStayLoggedIn = { screen = Screen.Profile },
                        onLogOut = {
                            // TODO: پاک کردن توکن/نشست واقعی
                            userEmail = ""
                            firstName = ""
                            lastName = ""
                            userLevelName = null
                            englishLevelName = null
                            avatarIndex = null
                            screen = Screen.Login
                        }
                    )
                }
            }
        }
    }
}