package com.example.bangkit_2024_ip_bpaai.data.remote.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_ip_bpaai.data.Result
import com.example.bangkit_2024_ip_bpaai.data.remote.response.RegisterResponse
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.*

class StoryRepository(private val apiService: ApiService) {
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("SignUp", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }
}