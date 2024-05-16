package com.example.bangkit_2024_ip_bpaai.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.bangkit_2024_ip_bpaai.data.remote.repository.StoryRepository
import com.example.bangkit_2024_ip_bpaai.di.Injection
import com.example.bangkit_2024_ip_bpaai.ui.authentication.signup.SignUpViewModel

class ViewModelFactory(private val storyRepository: StoryRepository): NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository())
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}