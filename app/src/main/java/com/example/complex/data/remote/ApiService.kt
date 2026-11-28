package com.example.complex.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    // اطلاعات کاربر واردشده از MongoDB
    @GET("api/auth/me")
    suspend fun getMe(): UserResponse

    // مسیرها و مراحل یادگیری از MongoDB
    @GET("api/learning/path")
    suspend fun getLearningPath(): List<LearningPathDto>

    @POST("api/learning/complete-step")
    suspend fun completeLearningStep(
        @Body request: CompleteStepRequest
    ): CompleteStepResponse
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
    val lives: Int,
    val level: Int = 1,
    val englishLevel: String? = null,
    val userType: String? = null,
    val interests: List<String> = emptyList(),
    val completedSteps: List<String> = emptyList(),
    val avatar: String? = null
)

data class UserResponse(
    val user: LoginUser
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String,
    val token: String? = null,
    val user: LoginUser? = null
)

data class LearningPathDto(
    val _id: String,
    val title: String,
    val description: String? = null,
    val category: String? = null,
    val steps: List<LearningStepDto> = emptyList(),
    val words: List<LearningWordDto> = emptyList()
)

data class LearningStepDto(
    val _id: String? = null,
    val title: String,
    val type: String? = null,
    val xpReward: Int = 30,
    val content: String? = null
)

data class LearningWordDto(
    val _id: String? = null,
    val englishWord: String? = null,
    val definition: String? = null
)

data class CompleteStepRequest(
    val userId: String,
    val stepId: String,
    val xpReward: Int,
    val isCorrect: Boolean
)

data class CompleteStepResponse(
    val message: String,
    val xp: Int,
    val level: Int,
    val lives: Int
)
