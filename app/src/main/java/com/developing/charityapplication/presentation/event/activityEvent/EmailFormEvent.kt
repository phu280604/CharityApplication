package com.developing.charityapplication.presentation.event.activityEvent

sealed class EmailFormEvent {

    // region -- Value Change --
    data class EmailChange(val email: String): EmailFormEvent()
    // endregion

    object Submit: EmailFormEvent()
}