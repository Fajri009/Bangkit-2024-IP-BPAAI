package com.example.bangkit_2024_ip_bpaai.ui.authentication.signup

import androidx.lifecycle.ViewModel
import com.example.bangkit_2024_ip_bpaai.data.remote.repository.StoryRepository

class SignUpViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun register(name: String, email: String, password: String) = storyRepository.register(name, email, password)
}