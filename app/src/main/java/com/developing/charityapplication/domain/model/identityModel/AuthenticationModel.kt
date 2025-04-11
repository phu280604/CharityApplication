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
    val email: String = ""
)

data class RequestOTPLoginM(
    val otp: String = ""
)

data class RequestResetPasswordM(
    val newPassword: String = "",
    val token: String = ""
)

data class RequestLogoutM(
    val token: String = ""
)

data class RequestTokenM(
    val token: String = ""
)