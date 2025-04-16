package com.developing.charityapplication.presentation.state.activityState

data class LoginFormState (
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)

data class LoginUIState(
    val value: String = "",
    val valueError: String? = null,
    val title: Int = 0,
    val icon: Int = 0
)