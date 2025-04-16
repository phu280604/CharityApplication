package com.developing.charityapplication.data.repository.postRepo

import android.util.Log
import com.developing.charityapplication.data.api.identityService.AuthAPI
import com.developing.charityapplication.data.api.postsService.PostsAPI
import com.developing.charityapplication.data.api.profileService.UserProfilesAPI
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.domain.repoInter.postsRepoInter.IPostRepo
import com.developing.charityapplication.domain.repoInter.profileRepoInter.IProfileRepo
import com.developing.charityapplication.infrastructure.utils.JsonConverter
import com.developing.charityapplication.infrastructure.utils.Logger
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class PostRepo @Inject constructor(
    private val apiPost : PostsAPI
): IPostRepo {

    // region --- Overrides ---

    override suspend fun getPostsByProfileId(profileId: String): ResponseM<List<ResponsePostM>>? {
        try {
            val response = apiPost.getPostsByProfileId(profileId)

            if (response.isSuccessful)
            {
                val result = response.body()!!
                Log.d("Json", Gson().toJson(result).toString())
                Logger.log(response, result.message)
                return result
            }

            val errorString = response.errorBody()?.string() ?: "{}"
            val result: ResponseM<List<ResponsePostM>> = JsonConverter.fromJson(errorString)
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