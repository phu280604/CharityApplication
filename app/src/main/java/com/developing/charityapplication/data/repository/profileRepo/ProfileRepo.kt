package com.developing.charityapplication.data.repository.profileRepo

import android.util.Log
import com.developing.charityapplication.data.api.profileService.UserProfilesAPI
import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.repoInter.profileRepoInter.IProfileRepo
import com.developing.charityapplication.infrastructure.utils.ConverterData
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileRepo @Inject constructor(
    private val apiProfile : UserProfilesAPI
): IProfileRepo {

    // region --- Overrides ---

    override suspend fun getProfile(): ResponseM<List<ResponseProfilesM>>? {
        try {
            val response = apiProfile.getProfilesUser()

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<List<ResponseProfilesM>> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun getProfileByProfileId(profileId: String): ResponseM<ResponseProfilesM>? {
        try {
            val response = apiProfile.getProfileByProfileId(profileId)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResponseProfilesM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun updateProfile(
        profileId: String,
        profileInfo: RequestUpdateProfileM,
        avatar: MultipartBody.Part?
    ): ResponseM<ResponseProfilesM>? {
        try {
            val response = apiProfile.updateProfileUser(profileId, profileInfo, avatar)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<ResponseProfilesM> = ConverterData.fromJson(errorString)
            Logger.log(response, response.message())

            return result
        }
        catch (ex: Exception){
            Log.d("Error", "Error: $ex")
            return null
        }
    }

    override suspend fun setActiveProfile(profileId: String): ResponseM<String>? {
        try {
            val response = apiProfile.setActiveProfile(profileId = profileId)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<String> = ConverterData.fromJson(errorString)
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