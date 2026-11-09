package com.example.complex // پکیج خود را جایگزین کنید

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.complex.ui.LoginScreen
import com.example.complex.ui.theme.CompLexTheme // نام تم پروژه خود را جایگزین کنید

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // تم برنامه خود را اینجا اعمال کنید
            CompLexTheme(){
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // ساخت کنترلر ناوبری
    val navController = rememberNavController()

    // تعریف تمام مسیرهای (Routes) برنامه
    NavHost(
        navController = navController,
        startDestination = "login_screen" // صفحه‌ای که برنامه با آن شروع می‌شود
    ) {

        // ۱. تعریف مسیر صفحه لاگین
        composable("login_screen") {
            LoginScreen(
                onLoginSuccess = {
                    // وقتی لاگین موفق شد، به صفحه اصلی (Home) برو
                    navController.navigate("home_screen") {
                        // این خط باعث می‌شود صفحه لاگین از حافظه پاک شود تا کاربر با زدن دکمه Back دوباره به لاگین برنگردد
                        popUpTo("login_screen") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    // وقتی کاربر روی دکمه ثبت‌نام کلیک کرد، به صفحه ثبت‌نام برو
                    navController.navigate("register_screen")
                }
            )
        }

        // ۲. تعریف مسیر صفحه ثبت‌نام (فقط یک نمونه ساده)
        composable("register_screen") {
            // اینجا باید کد صفحه ثبت‌نام خود را صدا بزنید
            // RegisterScreen(onNavigateBack = { navController.popBackStack() })

            // برای اینکه کد الان ارور ندهد، یک متن موقت گذاشتم:
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("صفحه ثبت‌نام (CreateAccount)")
            }
        }

        // ۳. تعریف مسیر صفحه اصلی (Home)
        composable("home_screen") {
            // اینجا باید کد صفحه اصلی خود را صدا بزنید
            // HomeScreen()

            // برای اینکه کد الان ارور ندهد، یک متن موقت گذاشتم:
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("صفحه اصلی برنامه (Home)")
            }
        }
    }
}