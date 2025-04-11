package com.developing.charityapplication.presentation.event.activityEvent

sealed class LoginChangeEvent {
    data class usernameValueChange(val username: String): LoginChangeEvent()
    data class passwordValueChange(val password: String): LoginChangeEvent()

    object Submit: LoginChangeEvent()
}