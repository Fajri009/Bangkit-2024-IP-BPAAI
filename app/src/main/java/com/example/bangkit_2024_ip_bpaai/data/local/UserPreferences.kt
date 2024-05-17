package com.example.bangkit_2024_ip_bpaai.data.local

import android.content.Context

class UserPreferences(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: User) {
        val editor = preferences.edit()
        editor.putString(TOKEN, "Bearer ${value.token}")
        editor.apply()
    }

    fun getUser(): User {
        val user = User()
        user.token = preferences.getString(TOKEN, "")

        return user
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val TOKEN = "token"
    }
}