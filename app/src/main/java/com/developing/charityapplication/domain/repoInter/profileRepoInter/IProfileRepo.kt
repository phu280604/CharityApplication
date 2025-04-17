package com.developing.charityapplication.domain.repoInter.profileRepoInter

import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import okhttp3.MultipartBody

interface IProfileRepo {

    // region --- Methods ---

    suspend fun getProfile() : ResponseM<List<ResponseProfilesM>>?

    suspend fun getProfileByProfileId(profileId: String) : ResponseM<ResponseProfilesM>?

    suspend fun updateProfile(
        profileId: String,
        profileInfo: RequestUpdateProfileM,
        avatar: MultipartBody.Part?
    ): ResponseM<ResponseProfilesM>?

    suspend fun setActiveProfile(profileId: String): ResponseM<String>?

    // endregion
}