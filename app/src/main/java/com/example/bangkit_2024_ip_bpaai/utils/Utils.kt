package com.example.bangkit_2024_ip_bpaai.utils

fun isValidEmail(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}