package com.developing.charityapplication.presentation.state.activityState

data class LoginState(
    val usernameValue: String = "",
    val usernameValueError: String? = null,
    val passwordValue: String = "",
    val passwordValueError: String? = null
)