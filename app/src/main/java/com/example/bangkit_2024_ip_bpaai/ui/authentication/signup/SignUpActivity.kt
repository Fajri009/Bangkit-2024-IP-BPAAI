package com.example.bangkit_2024_ip_bpaai.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.databinding.ActivitySignUpBinding
import com.example.bangkit_2024_ip_bpaai.ui.authentication.login.LoginActivity
import com.example.bangkit_2024_ip_bpaai.ui.home.HomeActivity
import com.example.bangkit_2024_ip_bpaai.utils.isValidEmail

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        login()
        signUp()
    }

    private fun login() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUp() {
        val edName = binding.edName.text
        val edEmail = binding.edEmail.text
        val edPassword = binding.edPassword.text

        binding.btnSignUp.setOnClickListener {
            if (edName!!.isEmpty() || edEmail!!.isEmpty() || edPassword!!.isEmpty()) {
                Toast.makeText(this@SignUpActivity, R.string.empty_form, Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(edEmail.toString()) || edPassword.length < 8) {
                Toast.makeText(this@SignUpActivity, R.string.invalid_form, Toast.LENGTH_SHORT).show()
                Log.i("test", edEmail.toString())
            } else {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@SignUpActivity, R.string.try_login, Toast.LENGTH_SHORT).show()
            }
        }
    }
}