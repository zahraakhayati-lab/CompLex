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
import com.example.complex.ui.LoginScreen
import com.example.complex.ui.SignUpScreen
import com.example.complex.ui.WelcomeScreen

private enum class Screen { Welcome, Login, SignUp }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme { // اگر تم خودتان را دارید (مثلا CompLexTheme) به‌جای آن بگذارید
                var screen by rememberSaveable { mutableStateOf(Screen.Welcome) }

                // دکمه‌ی Back گوشی
                BackHandler(enabled = screen != Screen.Welcome) {
                    screen = if (screen == Screen.SignUp) Screen.Login else Screen.Welcome
                }

                when (screen) {
                    Screen.Welcome -> WelcomeScreen(
                        onGetStarted = { screen = Screen.Login }
                        // وقتی عکس‌ها را در res/drawable گذاشتید:
                        // logo = painterResource(R.drawable.complex_logo),
                        // illustration = painterResource(R.drawable.welcome_illustration),
                    )

                    Screen.Login -> LoginScreen(
                        onBack = { screen = Screen.Welcome },
                        onLoginSuccess = { /* TODO: رفتن به صفحه‌ی اصلی */ },
                        onNavigateToRegister = { screen = Screen.SignUp }
                    )

                    Screen.SignUp -> SignUpScreen(
                        onBack = { screen = Screen.Login },
                        onCreateAccount = { email, password -> /* TODO: اتصال به ViewModel */ },
                        onNavigateToLogin = { screen = Screen.Login }
                    )
                }
            }
        }
    }
}