package com.example.bangkit_2024_ip_bpaai.ui.auth.signup

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.databinding.ActivitySignUpBinding
import com.example.bangkit_2024_ip_bpaai.ui.auth.login.LoginActivity
import com.example.bangkit_2024_ip_bpaai.utils.isValidEmail

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        playAnimation()
        login()
        signUp()

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                if (errorMessage == "SIGN UP ERROR : Email is already taken") {
                    Toast.makeText(this@SignUpActivity, R.string.already_taken, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                showToast(R.string.try_login)
            }
        }
    }

    private fun playAnimation() {
        val ivLogo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(1000)
        val tvSignUp = ObjectAnimator.ofFloat(binding.tvSignUp, View.ALPHA, 1f).setDuration(800)
        val tvEnter = ObjectAnimator.ofFloat(binding.tvEnter, View.ALPHA, 1f).setDuration(800)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(800)
        val edRegisterName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(800)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(800)
        val edRegisterEmail = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(800)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(800)
        val edRegisterPassword = ObjectAnimator.ofFloat(binding.edLayoutRegisterPassword, View.ALPHA, 1f).setDuration(800)
        val linearLayout = ObjectAnimator.ofFloat(binding.linearLayout, View.ALPHA, 1f).setDuration(800)
        val btnSignUp = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(800)

        AnimatorSet().apply {
            playSequentially(ivLogo, tvSignUp, tvEnter, tvName, edRegisterName, tvEmail, edRegisterEmail, tvPassword, edRegisterPassword, linearLayout, btnSignUp)
            startDelay = 100
            start()
        }
    }

    private fun login() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUp() {
        val edRegisterName = binding.edRegisterName.text
        val edRegisterEmail = binding.edRegisterEmail.text
        val edRegisterPassword = binding.edRegisterPassword.text

        binding.btnSignUp.setOnClickListener {
            if (edRegisterName!!.isEmpty() || edRegisterEmail!!.isEmpty() || edRegisterPassword!!.isEmpty()) {
                showToast(R.string.empty_form)
            } else if (!isValidEmail(edRegisterEmail.toString()) || edRegisterPassword.length < 8) {
                showToast(R.string.invalid_form)
            } else {
                viewModel.register(
                    edRegisterName.toString(),
                    edRegisterEmail.toString(),
                    edRegisterPassword.toString()
                )
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
        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
    }
}