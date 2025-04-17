package com.developing.charityapplication.presentation.event.screenEvent

import okhttp3.MultipartBody

sealed class CreatingPostEvent {

    // region -- Value Change --
    data class ContentChange(val content: String): CreatingPostEvent()
    data class StartDateChange(val startDate: String): CreatingPostEvent()
    data class EndDateChange(val endDate: String): CreatingPostEvent()
    data class FilesChange(val files: List<MultipartBody.Part>?): CreatingPostEvent()
    // endregion

    object Submit: CreatingPostEvent()
}