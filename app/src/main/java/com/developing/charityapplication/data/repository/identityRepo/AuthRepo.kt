package com.developing.charityapplication.data.repository.identityRepo

import android.util.Log
import com.developing.charityapplication.data.api.identityService.AuthAPI
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.infrastructure.utils.ConverterData
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val apiAuth : AuthAPI
): IAuthRepo {

    // region --- Overrides ---

    override suspend fun defaultLogin(loginInfo: RequestLoginM): ResponseM<Result>? {
        try {
            Log.d("Json", Gson().toJson(loginInfo).toString())
            val response = apiAuth.defaultLogin(loginInfo)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<Result> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }

    }

    override suspend fun defaultLogout(authRequest: RequestLogoutM): ResponseM<ResultM>? {
        try {
            val response = apiAuth.defaultLogout(authRequest)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResultM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun sendOtp_email(email: RequestEmailM): ResponseM<ResultM>? {
        try {
            val response = apiAuth.sendOtp_Email(email)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResultM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun verifyOtp_email(otp: RequestOTPM): ResponseM<ResultM>? {
        try {
            val response = apiAuth.verifyEmailWithOtp(otp)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResultM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun sendOtp_ResetPassword(authRequest: RequestEmailM): ResponseM<ResultM>? {
        try {
            val response = apiAuth.sendOtp_ResetPassword(authRequest)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResultM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun verifyOtpToResetPassword(otp: String): ResponseM<ResponseVerifyResetPasswordM>? {
        try {
            val response = apiAuth.verifyOtpToResetPassword(otp)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResponseVerifyResetPasswordM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun resetPassword(newPassword: RequestResetPasswordM): ResponseM<ResultM>? {
        try {
            val response = apiAuth.resetPassword(newPassword)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResultM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    // endregion
}