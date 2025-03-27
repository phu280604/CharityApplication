package com.developing.charityapplication.data.authentication

import android.content.SharedPreferences

class TokenProvider(private val sharedPreferences: SharedPreferences) {
    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }
}
