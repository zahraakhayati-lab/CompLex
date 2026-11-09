package com.example.complex.ui // پکیج خود را جایگزین کنید

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(), // اتصال به ViewModel
    onLoginSuccess: () -> Unit,              // اکشن رفتن به صفحه بعد
    onNavigateToRegister: () -> Unit         // اکشن رفتن به صفحه ثبت‌نام
) {
    // استیت‌های محلی برای نگه داشتن متن‌های وارد شده
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // استیت برای نمایش یا مخفی کردن رمز عبور
    var passwordVisible by remember { mutableStateOf(false) }

    // وقتی لاگین موفق شد، به صفحه بعدی برو
    LaunchedEffect(viewModel.isLoginSuccessful) {
        if (viewModel.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    // یک باکس اصلی که کل صفحه را پر می‌کند
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            // --- عنوان صفحه ---
            Text(
                text = "خوش آمدید",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "برای ادامه وارد حساب خود شوید",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- فیلد نام کاربری ---
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("نام کاربری / ایمیل") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isLoading // در زمان لودینگ غیرفعال شود
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- فیلد رمز عبور ---
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("رمز عبور") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                // منطق نمایش/مخفی کردن رمز
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = "تغییر نمایش رمز عبور")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !viewModel.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- نمایش پیام خطا (در صورت وجود) ---
            if (viewModel.errorMessage != null) {
                Text(
                    text = viewModel.errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- دکمه ورود / نشانگر لودینگ ---
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Button(
                    onClick = {
                        // اعتبارسنجی ساده قبل از ارسال
                        if (username.isBlank() || password.isBlank()) {
                            // می‌توانید اینجا به کاربر بگویید فیلدها را پر کند
                        } else {
                            viewModel.loginUser(username, password)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "ورود", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- لینک رفتن به ثبت‌نام ---
            TextButton(
                onClick = onNavigateToRegister,
                enabled = !viewModel.isLoading
            ) {
                Text("حساب کاربری ندارید؟ ثبت‌نام کنید")
            }
        }
    }
}