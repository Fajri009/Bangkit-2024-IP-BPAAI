package com.example.bangkit_2024_ip_bpaai.ui.authentication.login

import android.animation.*
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.databinding.ActivityLoginBinding
import com.example.bangkit_2024_ip_bpaai.ui.authentication.signup.SignUpActivity
import com.example.bangkit_2024_ip_bpaai.ui.home.HomeActivity
import com.example.bangkit_2024_ip_bpaai.utils.isValidEmail

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        signUp()
        login()
    }

    private fun playAnimation() {
        val ivLogo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(1000)
        val tvLogin = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(800)
        val tvEnter = ObjectAnimator.ofFloat(binding.tvEnter, View.ALPHA, 1f).setDuration(800)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(800)
        val edLoginEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(800)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(800)
        val edLoginPassword = ObjectAnimator.ofFloat(binding.edLayoutLoginPassword, View.ALPHA, 1f).setDuration(800)
        val linearLayout = ObjectAnimator.ofFloat(binding.linearLayout, View.ALPHA, 1f).setDuration(800)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(800)

        AnimatorSet().apply {
            playSequentially(ivLogo, tvLogin, tvEnter, tvEmail, edLoginEmail, tvPassword, edLoginPassword, linearLayout, btnLogin)
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
                Toast.makeText(this@LoginActivity, R.string.empty_form, Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(edLoginEmail.toString()) || edLoginPassword.length < 8) {
                Toast.makeText(this@LoginActivity, R.string.invalid_form, Toast.LENGTH_SHORT).show()
                Log.i("test", edLoginEmail.toString())
            } else {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}