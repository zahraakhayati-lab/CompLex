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

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isLoginSuccessful = false

            try {
                RetrofitInstance.service.login(LoginRequest(email.trim(), password))
                isLoginSuccessful = true
            } catch (e: Exception) {
                errorMessage = "Error in server connection: ${e.message ?: "Unknown error"}"
            } finally {
                isLoading = false
            }
        }
    }
}
