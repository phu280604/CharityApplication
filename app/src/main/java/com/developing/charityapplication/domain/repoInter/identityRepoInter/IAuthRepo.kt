package com.developing.charityapplication.domain.repoInter.identityRepoInter

import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestLogoutM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.domain.model.identityModel.RequestResetPasswordM
import com.developing.charityapplication.domain.model.identityModel.ResponseVerifyResetPasswordM
import com.developing.charityapplication.domain.model.identityModel.Result
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

interface IAuthRepo {

    // region --- Methods ---

    suspend fun defaultLogin(loginInfo: RequestLoginM) : ResponseM<Result>?

    suspend fun defaultLogout(authRequest: RequestLogoutM): ResponseM<ResultM>?

    suspend fun sendOtp_email(email: RequestEmailM) : ResponseM<ResultM>?

    suspend fun verifyOtp_email(otp: RequestOTPM): ResponseM<ResultM>?

    suspend fun sendOtp_ResetPassword(authRequest: RequestEmailM): ResponseM<ResultM>?

    suspend fun verifyOtpToResetPassword(otp: String): ResponseM<ResponseVerifyResetPasswordM>?

    suspend fun resetPassword(newPassword: RequestResetPasswordM): ResponseM<ResultM>?

    // endregion
}