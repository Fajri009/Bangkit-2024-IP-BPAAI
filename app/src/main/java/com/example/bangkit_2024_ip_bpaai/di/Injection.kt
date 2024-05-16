package com.example.bangkit_2024_ip_bpaai.di

import com.example.bangkit_2024_ip_bpaai.data.remote.repository.StoryRepository
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}