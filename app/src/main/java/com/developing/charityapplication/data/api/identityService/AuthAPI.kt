package com.developing.charityapplication.data.api.identityService

import com.developing.charityapplication.domain.model.identityModel.AuthenticationM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginAuthM
import retrofit2.http.Body

interface AuthAPI {

    // region --- Methods ---

    suspend fun loginService(@Body authRequest: RequestLoginAuthM): ResponseM<AuthenticationM>

    // endregion

}