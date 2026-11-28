package com.example.complex.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
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
    val name: String,
    val email: String,
    val streak: Int,
    val loginDays: List<Int>,
    val xp: Int,
    val lives: Int
)


data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String
)


