package com.example.bangkit_2024_ip_bpaai.utils

import java.text.*
import java.util.*

fun isValidEmail(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = format.parse(this) as Date

    val outputFormat = SimpleDateFormat("dd - MMMM - yyyy", Locale.getDefault())
    return outputFormat.format(date)
}