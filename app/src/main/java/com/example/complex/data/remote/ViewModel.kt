package com.example.complex.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.complex.data.remote.LoginRequest
import com.example.complex.data.remote.RetrofitInstance
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isLoginSuccessful by mutableStateOf(false)
        private set

    var loggedInUserId by mutableStateOf<String?>(null)
        private set

    var loggedInUserEmail by mutableStateOf<String?>(null)
        private set

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isLoginSuccessful = false
            loggedInUserId = null
            loggedInUserEmail = null

            try {
                val response = RetrofitInstance.service.login(
                    LoginRequest(email = email.trim(), password = password)
                )
                loggedInUserId = response.user.id
                loggedInUserEmail = response.user.email ?: email.trim()
                isLoginSuccessful = true
            } catch (e: Exception) {
                errorMessage = "Login failed: ${e.message ?: "Please try again"}"
            } finally {
                isLoading = false
            }
        }
    }
}
