package com.developing.charityapplication.infrastructure.utils

import android.util.Log
import retrofit2.Response

object Logger {
    fun <T> log(response: Response<T>){
        Log.d("Response_Value", "Success: ${response.isSuccessful}")
        Log.d("Response_Value", "Code: ${response.code()}")
        Log.d("Response_Value", "Sms: ${response.errorBody()?.string()}")
    }

    fun logJson(json: String){
        Log.d("Json_Convert", "Json: $json")
    }
}