package com.example.complex.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL) // مطمئن شوید ApiConfig.BASE_URL تعریف شده است
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // این خط جدید باعث می‌شود سرویس ما آماده استفاده شود
    val service: ApiService by lazy {
        api.create(ApiService::class.java)
    }
}