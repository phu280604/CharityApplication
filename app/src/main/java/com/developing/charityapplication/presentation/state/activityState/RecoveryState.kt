package com.developing.charityapplication.presentation.state.activityState

data class RecoveryState(
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
)