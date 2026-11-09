package com.example.complex.ui // پکیج را بر اساس ساختار پروژه خود تغییر دهید

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.complex.data.remote.LoginRequest
import com.example.complex.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    // این متغیرها برای این هستند که صفحه (UI) بفهمد چه خبر است
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoginSuccessful by mutableStateOf(false)
        private set

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isLoginSuccessful = false

            try {
                // درخواست به سرور
                val response = RetrofitInstance.service.login(LoginRequest(username, password))

                // اگر به اینجا رسید یعنی درخواست موفق بوده (کد 200)
                isLoading = false
                isLoginSuccessful = true
                // می‌توانید توکن را در اینجا ذخیره کنید: response.token

            } catch (e: Exception) {
                // اگر خطایی رخ داد (مثلاً اینترنت قطع بود یا سرور ارور داد)
                isLoading = false
                errorMessage = "Error in server connection ${e.message}"
            }
        }
    }
}