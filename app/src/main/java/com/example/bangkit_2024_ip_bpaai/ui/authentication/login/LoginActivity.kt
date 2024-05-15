package com.example.bangkit_2024_ip_bpaai.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        signUp()
        login()
    }

    private fun signUp() {
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val edEmail = binding.edEmail.text
        val edPassword = binding.edPassword.text

        binding.btnLogin.setOnClickListener {
            if (edEmail!!.isEmpty() || edPassword!!.isEmpty()) {
                Toast.makeText(this@LoginActivity, R.string.empty_form, Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(edEmail.toString()) || edPassword.length < 8) {
                Toast.makeText(this@LoginActivity, R.string.invalid_form, Toast.LENGTH_SHORT).show()
                Log.i("test", edEmail.toString())
            } else {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}