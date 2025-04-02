package com.developing.charityapplication.infrastructure.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonConverter {
    fun <T> toJson(data: T): String{
        return Gson().toJson(data)
    }

    inline fun <reified T> fromJson(data: String): T {
        return Gson().fromJson(data, T::class.java)
    }
}