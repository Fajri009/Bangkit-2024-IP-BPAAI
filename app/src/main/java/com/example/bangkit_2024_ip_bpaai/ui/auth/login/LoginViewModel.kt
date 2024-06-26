package com.example.bangkit_2024_ip_bpaai.ui.auth.login

import android.util.Log
import androidx.lifecycle.*
import com.example.bangkit_2024_ip_bpaai.data.remote.response.LoginResponse
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.ApiConfig
import org.json.JSONObject
import retrofit2.*

class LoginViewModel: ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun login(email: String, password: String) {
        _isLoading.value = true
        _isSuccess.value = false

        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _loginResult.value = response.body()
                    _isSuccess.value = true
                } else {
                    response.errorBody()?.let {
                        val errorResponse = JSONObject(it.string())
                        val errorMessages = errorResponse.getString("message")
                        _errorMessage.postValue("LOGIN ERROR : $errorMessages")
                    }
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, e: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        })
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}