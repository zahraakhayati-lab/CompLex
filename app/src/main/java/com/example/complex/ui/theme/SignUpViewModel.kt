package com.example.complex.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.complex.data.remote.RegisterRequest
import com.example.complex.data.remote.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SignUpViewModel : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isRegisterSuccessful by mutableStateOf(false)
        private set

    var registeredUserId by mutableStateOf("")
        private set

    var registeredEmail by mutableStateOf("")
        private set

    fun registerUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isRegisterSuccessful = false

            try {
                val response = RetrofitInstance.service.register(
                    RegisterRequest(
                        name = "User",
                        email = email.trim().lowercase(),
                        password = password
                    )
                )

                registeredEmail = email.trim().lowercase()
                registeredUserId = ""
                isRegisterSuccessful = true

            } catch (e: HttpException) {
                errorMessage = when (e.code()) {
                    400 -> "This email is already registered or the information is invalid."
                    else -> "Server error: ${e.code()}"
                }
            } catch (e: IOException) {
                errorMessage = "Could not connect to the server."
            } catch (e: Exception) {
                errorMessage = e.message ?: "An unexpected error occurred."
            } finally {
                isLoading = false
            }
        }
    }

    fun resetRegisterState() {
        isRegisterSuccessful = false
        registeredUserId = ""
        registeredEmail = ""
        errorMessage = null
    }
}
