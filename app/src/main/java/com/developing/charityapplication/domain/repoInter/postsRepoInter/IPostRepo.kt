package com.developing.charityapplication.domain.repoInter.postsRepoInter

import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.domain.model.identityModel.Result
import com.developing.charityapplication.domain.model.postModel.RequestPostContentM
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.postModel.ResponsePostsByProfileId
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface IPostRepo {

    // region --- Methods ---

    suspend fun createPost(
        postRequest: RequestPostContentM,
        files: List<MultipartBody.Part>
    ): ResponseM<ResponsePostM>?

    suspend fun updatePost(
        postId: String,
        filesToRemove: List<String>,
        postUpdateRequest: RequestPostContentM,
        files: List<MultipartBody.Part>?
    ): ResponseM<ResponsePostM>?

    suspend fun deletePost(postId: String) : ResponseM<String>?

    suspend fun getAllPosts() : ResponseM<List<ResponsePostM>>?

    suspend fun getPostsByProfileId(profileId: String): ResponseM<ResponsePostsByProfileId>?

    // endregion
}