package com.developing.charityapplication.domain.model.auth

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