package com.developing.charityapplication.presentation.state.activityState

data class LoginFormState (
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)