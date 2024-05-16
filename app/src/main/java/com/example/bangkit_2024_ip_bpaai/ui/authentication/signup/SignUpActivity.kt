package com.example.bangkit_2024_ip_bpaai.ui.authentication.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.data.Result
import com.example.bangkit_2024_ip_bpaai.databinding.ActivitySignUpBinding
import com.example.bangkit_2024_ip_bpaai.ui.ViewModelFactory
import com.example.bangkit_2024_ip_bpaai.ui.authentication.login.LoginActivity
import com.example.bangkit_2024_ip_bpaai.utils.isValidEmail

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val application = ViewModelFactory.getInstance(this@SignUpActivity.application)
        viewModel = ViewModelProvider(this@SignUpActivity, application).get(SignUpViewModel::class.java)

        playAnimation()
        login()
        signUp()
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
                Toast.makeText(this@SignUpActivity, R.string.empty_form, Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(edRegisterEmail.toString()) || edRegisterPassword.length < 8) {
                Toast.makeText(this@SignUpActivity, R.string.invalid_form, Toast.LENGTH_SHORT).show()
                Log.i("test", edRegisterEmail.toString())
            } else {
                viewModel.register(
                    edRegisterName.toString(),
                    edRegisterEmail.toString(),
                    edRegisterPassword.toString()
                ).observe(this@SignUpActivity) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@SignUpActivity, R.string.try_login, Toast.LENGTH_SHORT).show()
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@SignUpActivity, result.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}