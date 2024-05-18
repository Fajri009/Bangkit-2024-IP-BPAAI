package com.example.bangkit_2024_ip_bpaai.data.remote.retrofit

import com.example.bangkit_2024_ip_bpaai.BuildConfig
import com.example.bangkit_2024_ip_bpaai.data.remote.response.*
import okhttp3.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<Story>

    @Multipart
    @POST("stories")
    fun addStories(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<AddStoryResponse>
}
