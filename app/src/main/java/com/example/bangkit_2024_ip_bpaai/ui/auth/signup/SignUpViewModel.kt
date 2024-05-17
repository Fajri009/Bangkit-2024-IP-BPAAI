package com.example.bangkit_2024_ip_bpaai.ui.auth.signup

import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_ip_bpaai.data.remote.response.RegisterResponse
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.ApiConfig
import org.json.JSONObject
import retrofit2.*

class SignUpViewModel: ViewModel() {
    val signUp = MutableLiveData<RegisterResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        _isSuccess.value = false

        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    signUp.postValue(response.body())
                    _isSuccess.value = true
                } else {
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        _errorMessage.postValue("SIGN UP ERROR : $errorMessages")
                    }
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, e: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        })
    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }
}