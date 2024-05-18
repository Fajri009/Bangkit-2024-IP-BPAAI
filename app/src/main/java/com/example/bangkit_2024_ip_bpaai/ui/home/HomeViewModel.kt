package com.example.bangkit_2024_ip_bpaai.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_ip_bpaai.data.remote.response.ListStoryItem
import com.example.bangkit_2024_ip_bpaai.data.remote.response.Story
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.ApiConfig
import retrofit2.*

class HomeViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    fun getStories(token: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getStories("Bearer $token")
        client.enqueue(object: Callback<Story> {
            override fun onResponse(
                call: Call<Story>,
                response: Response<Story>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val list = response.body()?.listStory
                    _listStory.value = list as List<ListStoryItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Story>, e: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        })
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}