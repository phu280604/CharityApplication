package com.developing.charityapplication.data.repository.identityRepo

import android.util.Log
import com.developing.charityapplication.data.api.identityService.AuthAPI
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.infrastructure.utils.JsonConverter
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val apiAuth : AuthAPI
): IAuthRepo {

    // region --- Methods ---

    override suspend fun defaultLogin(loginInfo: RequestLoginM): ResponseM<Result>? {
        try {
            //Log.d("Json", Gson().toJson(loginInfo).toString())
            val response = apiAuth.defaultLogin(loginInfo)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<Result> = JsonConverter.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Login", "Error: $ex")
            return null
        }

    }
}