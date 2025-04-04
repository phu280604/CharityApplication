package com.developing.charityapplication.domain.model.identityModel

data class AuthenticationM(
    val token: String? = null,
    val authenticated: String = "",
    val message: String = "",
    val otpRequired: String = ""
)

data class RequestLoginAuthM(
    var username: String = "",
    var password: String = "",
)

data class EmailM(
    val email: String = ""
)

data class OTPLoginM(
    val otp: String = ""
)

data class ResetPasswordM(
    val newPassword: String = "",
    val token: String = ""
)

data class RequestLogoutAuthM(
    val token: String = ""
)

data class TokenM(
    val token: String = ""
)