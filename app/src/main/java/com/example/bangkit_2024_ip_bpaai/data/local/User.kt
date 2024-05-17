package com.example.bangkit_2024_ip_bpaai.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var token: String? = ""
): Parcelable