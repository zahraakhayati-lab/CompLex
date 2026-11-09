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

private enum class Screen { Login, SignUp }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme { // اگر تم خودتان را دارید (مثلا CompLexTheme) به‌جای آن بگذارید
                var screen by rememberSaveable { mutableStateOf(Screen.Login) }

                BackHandler(enabled = screen == Screen.SignUp) { screen = Screen.Login }

                when (screen) {
                    Screen.Login -> LoginScreen(
                        onBack = { finish() },
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