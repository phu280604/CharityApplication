package com.developing.charityapplication.presentation.event.activityEvent

sealed class LoginFormEvent {

    // region -- Value Change --
    data class usernameChange(val username: String): LoginFormEvent()
    data class passwordChange(val password: String): LoginFormEvent()
    // endregion

    object Submit: LoginFormEvent()
}