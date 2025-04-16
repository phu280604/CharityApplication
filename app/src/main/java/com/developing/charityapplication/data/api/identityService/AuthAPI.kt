package com.developing.charityapplication.data.api.identityService

import com.developing.charityapplication.domain.model.identityModel.AuthenticationM
import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestLogoutM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.domain.model.identityModel.RequestResetPasswordM
import com.developing.charityapplication.domain.model.identityModel.Result
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {

    // region --- Methods ---

    // Login
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/auth/token")
    suspend fun defaultLogin(@Body authRequest: RequestLoginM): Response<ResponseM<Result>>

    // Logout
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/auth/logout")
    suspend fun defaultLogout(@Body authRequest: RequestLogoutM): Response<ResponseM<ResultM>>

    // Sending OTP to Email
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/auth/send-verify-email-with-otp")
    suspend fun sendOtp_Email(@Body authRequest: RequestEmailM): Response<ResponseM<ResultM>>

    // Sending OTP to Email for Resetting Password
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/auth/forgot-password-otp")
    suspend fun sendOtp_ResetPassword(@Body authRequest: RequestEmailM): Response<ResponseM<ResultM>>

    // Verifying Email with OTP
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/auth/confirm-verify-email-otp")
    suspend fun verifyEmailWithOtp(@Body authRequest: RequestOTPM): Response<ResponseM<ResultM>>

    // Verifying Email to Reset Pass
    @Headers("Accept: */*")
    @POST("identity/auth/verify-otp-password")
    suspend fun verifyOtpToResetPassword(@Query("otp") otp: String): Response<ResponseM<ResultM>>

    // Resetting Password
    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("identity/auth/reset-password-otp")
    suspend fun resetPassword(@Body newPassword: RequestResetPasswordM): Response<ResponseM<ResultM>>

    // endregion

}