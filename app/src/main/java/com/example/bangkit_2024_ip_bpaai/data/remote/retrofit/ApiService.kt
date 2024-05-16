package com.example.bangkit_2024_ip_bpaai.data.remote.retrofit

import com.example.bangkit_2024_ip_bpaai.BuildConfig
import com.example.bangkit_2024_ip_bpaai.data.remote.response.LoginResponse
import com.example.bangkit_2024_ip_bpaai.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorizaion: token " + BuildConfig.BASE_API_URL)
    @FormUrlEncoded()
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded()
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}
