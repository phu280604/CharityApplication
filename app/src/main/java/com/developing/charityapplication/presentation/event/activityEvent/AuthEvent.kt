package com.developing.charityapplication.presentation.event.activityEvent

sealed class AuthChangeEvent {
    data class PinValueChange(val pinValue: String): AuthChangeEvent()
    object Submit: AuthChangeEvent()
}