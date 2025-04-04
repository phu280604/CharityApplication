package com.developing.charityapplication.presentation.state.activityState

import androidx.compose.ui.focus.FocusRequester

data class AuthenticationState(
    val pinValue: String = "",
    val pinValueError: String? = null
)

data class AuthenticationFocusRequest(
    val focusRequest: FocusRequester
)