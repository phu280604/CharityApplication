package com.developing.charityapplication.domain.repoInter.postsRepoInter

import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.domain.model.identityModel.Result
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import retrofit2.Response
import retrofit2.http.Body

interface IPostRepo {

    // region --- Methods ---

    suspend fun getPostsByProfileId(profileId: String): ResponseM<List<ResponsePostM>>?

    // endregion
}