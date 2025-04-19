package com.developing.charityapplication.presentation.event.activityEvent

sealed class RecoveryEvent {

    // region -- Value Change --
    data class PasswordChange(val password: String): RecoveryEvent()
    data class RepeatedPasswordChange(val repeatedPassword: String): RecoveryEvent()
    // endregion

    object Submit: RecoveryEvent()
}