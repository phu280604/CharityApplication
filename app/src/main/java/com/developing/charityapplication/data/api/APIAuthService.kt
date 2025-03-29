package com.developing.charityapplication.data.api

import com.developing.charityapplication.domain.model.auth.AuthenticationM
import com.developing.charityapplication.domain.model.ResponseM
import com.developing.charityapplication.domain.model.auth.RequestLoginAuthM
import retrofit2.http.Body

interface APIAuthService {

    // region --- Methods ---

    suspend fun loginService(@Body authRequest: RequestLoginAuthM): ResponseM<AuthenticationM>

    // endregion

}