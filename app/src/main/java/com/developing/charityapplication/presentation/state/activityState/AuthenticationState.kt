package com.developing.charityapplication.presentation.state.activityState

import androidx.compose.ui.focus.FocusRequester
import com.developing.charityapplication.presentation.event.activityEvent.AuthChangeEvent

data class AuthenticationState(
    val pinValue: String = "",
    val pinValueError: String? = null
)

data class AuthenticationFocusRequest(
    val focusRequest: FocusRequester
)

// region --- State UI ---
data class AuthStateVM(
    var states: List<AuthenticationState>,
    var focusRequests: List<FocusRequester>
)

data class AuthEventVM(
    var onChangeValue: (Int, String) -> Unit,
    var onSendOtp: () -> Unit,
    var onSubmit: () -> Unit
)
// endregion