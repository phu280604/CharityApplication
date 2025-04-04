package com.developing.charityapplication.data.repository.identityRepo

import android.util.Log
import com.developing.charityapplication.data.api.identityService.UsersAPI
import com.developing.charityapplication.domain.model.identityModel.RequestCreateUser
import com.developing.charityapplication.domain.model.identityModel.UserM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IUserRepo
import com.developing.charityapplication.infrastructure.utils.JsonConverter
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import javax.inject.Inject

class UsersRepo @Inject constructor(
    private val apiUser : UsersAPI
): IUserRepo {

    // region --- Methods ---

    override suspend fun createAccount(userInfo: RequestCreateUser): ResponseM<UserM>?{
        try {
            Log.d("Json", Gson().toJson(userInfo))
            val response = apiUser.createUser(userInfo)
            if (response.isSuccessful)
            {

                val result = response.body()!!
                Log.d("Json", Gson().toJson(userInfo))
                Logger.log(response, result.message)
                return result
            }


            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<UserM> = JsonConverter.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("CreateAccountUser", "Error: $ex")
            return null
        }

    }

    // endregion

}