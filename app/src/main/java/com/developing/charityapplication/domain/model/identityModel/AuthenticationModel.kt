package com.developing.charityapplication.domain.model.identityModel

data class AuthenticationM(
    val token: String? = null,
    val authenticated: String = "",
    val message: String = "",
    val otpRequired: String = ""
)

data class RequestLoginM(
    var username: String = "",
    var password: String = "",
)

data class ResultLoginM(
    val code: String = "",
    val result: Result
)

data class Result(
    val authenticated: String = "",
    val message: String = "",
    val otpRequired: String = "",
    val token: String = ""
)

data class RequestEmailM(
    var email: String = ""
)

data class RequestOTPM(
    var otp: String = ""
)

data class RequestResetPasswordM(
    val resetToken: String = "",
    val newPassword: String = ""
)

data class RequestLogoutM(
    val token: String = ""
)

data class ResponseVerifyResetPasswordM(
    val result: String,
    val resetToken: String
)