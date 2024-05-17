package com.example.bangkit_2024_ip_bpaai.ui.auth.login

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.data.local.User
import com.example.bangkit_2024_ip_bpaai.data.local.UserPreferences
import com.example.bangkit_2024_ip_bpaai.databinding.ActivityLoginBinding
import com.example.bangkit_2024_ip_bpaai.ui.auth.signup.SignUpActivity
import com.example.bangkit_2024_ip_bpaai.ui.home.HomeActivity
import com.example.bangkit_2024_ip_bpaai.utils.isValidEmail

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var userModel: User = User()
    private lateinit var userPreference: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreference = UserPreferences(this)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        playAnimation()
        signUp()
        login()

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun playAnimation() {
        val ivLogo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(1000)
        val tvLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(800)
        val tvEnter = ObjectAnimator.ofFloat(binding.tvEnter, View.ALPHA, 1f).setDuration(800)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(800)
        val edLoginEmail =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(800)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(800)
        val edLoginPassword =
            ObjectAnimator.ofFloat(binding.edLayoutLoginPassword, View.ALPHA, 1f).setDuration(800)
        val linearLayout =
            ObjectAnimator.ofFloat(binding.linearLayout, View.ALPHA, 1f).setDuration(800)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(800)

        AnimatorSet().apply {
            playSequentially(
                ivLogo,
                tvLogin,
                tvEnter,
                tvEmail,
                edLoginEmail,
                tvPassword,
                edLoginPassword,
                linearLayout,
                btnLogin
            )
            startDelay = 100
            start()
        }
    }

    private fun signUp() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val edLoginEmail = binding.edLoginEmail.text
        val edLoginPassword = binding.edLoginPassword.text

        binding.btnLogin.setOnClickListener {
            if (edLoginEmail!!.isEmpty() || edLoginPassword!!.isEmpty()) {
                showToast(R.string.empty_form)
            } else if (!isValidEmail(edLoginEmail.toString()) || edLoginPassword.length < 8) {
                showToast(R.string.invalid_form)
            } else {
                viewModel.login(
                    edLoginEmail.toString(),
                    edLoginPassword.toString()
                )

                viewModel.isSuccess.observe(this) {
                    if (it) {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }

                viewModel.loginResult.observe(this) { result ->
                    userModel.token = result.loginResult?.token
                    userPreference.setUser(userModel)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    private fun showToast(message: Int) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}