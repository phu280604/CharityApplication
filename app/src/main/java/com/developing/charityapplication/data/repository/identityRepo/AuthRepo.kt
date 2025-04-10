//package com.developing.charityapplication.data.repository.identityRepo
//
//import android.util.Log
//import com.developing.charityapplication.data.api.identityService.AuthAPI
//import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
//import com.developing.charityapplication.domain.model.identityModel.ResultLoginM
//import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
//import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
//import com.developing.charityapplication.infrastructure.utils.JsonConverter
//import com.developing.charityapplication.infrastructure.utils.Logger
//import com.google.gson.Gson
//import javax.inject.Inject
//
//class AuthRepo @Inject constructor(
//    private val apiAuth : AuthAPI
//): IAuthRepo {
//
//    // region --- Methods ---
//
//    override suspend fun login(loginInfo: RequestLoginM): ResponseM<ResultLoginM>? {
//        try {
//            Log.d("Json", Gson().toJson(loginInfo))
//            val response = apiAuth.loginService(loginInfo)
//            if (response.isSuccessful)
//            {
//
//                val result = response.body()!!
//                Log.d("Json", Gson().toJson(result))
//                Logger.log(response, result.message)
//                return result
//            }
//
//
//            val errorString = response.errorBody()?.string() ?: "{}"
//            val result: ResponseM<ResultLoginM> = JsonConverter.fromJson(errorString)
//            Logger.log(response, response.message())
//
//            return result
//        }
//        catch (ex: Exception){
//            Log.d("Login", "Error: $ex")
//            return null
//        }
//    }
//}