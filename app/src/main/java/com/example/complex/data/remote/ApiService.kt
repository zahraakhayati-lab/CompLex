package com.example.complex.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @PUT("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): MessageResponse
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val message: String,
    val user: LoginUser
)

data class LoginUser(
    val id: String,
    val name: String? = null,
    val email: String? = null
)

data class ChangePasswordRequest(
    val userId: String,
    val currentPassword: String,
    val newPassword: String
)

data class MessageResponse(
    val message: String
)
