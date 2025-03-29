package com.developing.charityapplication.data.repository

import android.util.Log
import com.developing.charityapplication.data.api.APIUserService
import com.developing.charityapplication.domain.model.*
import com.developing.charityapplication.domain.model.user.RequestCreateUser
import com.developing.charityapplication.domain.model.user.UserM
import com.developing.charityapplication.domain.repository.IUserRepo
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiUser : APIUserService
): IUserRepo {

    // region --- Methods ---

    override suspend fun createAccount(userInfo: RequestCreateUser): ResponseM<UserM>?{
        try {
            val response = apiUser.createUser(userInfo)
            if (response.isSuccessful)
            {
                val result = response.body()!!
                val json = Gson().toJson(result)
                Logger.logJson(json)
                Logger.log(response)

                return result
            }

            Logger.log(response)

            return null
        }
        catch (ex: Exception){
            Log.d("CreateAccountUser", "Error: $ex")
            return null
        }

    }

    // endregion

    // region --- Properties ---
    // endregion

}