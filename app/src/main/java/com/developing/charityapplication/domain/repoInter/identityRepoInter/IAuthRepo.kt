package com.developing.charityapplication.domain.repoInter.identityRepoInter

import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.domain.model.identityModel.Result
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import retrofit2.Response
import retrofit2.http.Body

interface IAuthRepo {

    // region --- Methods ---

    suspend fun defaultLogin(loginInfo: RequestLoginM) : ResponseM<Result>?

    suspend fun sendOtp_email(email: RequestEmailM) : ResponseM<ResultM>?

    suspend fun verifyOtp_email(otp: RequestOTPM): ResponseM<ResultM>?

    // endregion
}