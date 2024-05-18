package com.example.bangkit_2024_ip_bpaai.ui.add

import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_ip_bpaai.data.remote.response.AddStoryResponse
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.ApiConfig
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val addStory = MutableLiveData<AddStoryResponse>()

    fun addStories(token: String, uri: MultipartBody.Part, desc: RequestBody) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().addStories("Bearer $token", uri, desc)
        client.enqueue(object : Callback<AddStoryResponse> {
            override fun onResponse(call: Call<AddStoryResponse>, response: Response<AddStoryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    addStory.postValue(response.body())
                } else {
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        Log.e(TAG, "onFailure: $errorMessages")
                    }
                }
            }

            override fun onFailure(call: Call<AddStoryResponse>, e: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        })
    }

    companion object {
        private const val TAG = "AddStoryViewModel"
    }
}