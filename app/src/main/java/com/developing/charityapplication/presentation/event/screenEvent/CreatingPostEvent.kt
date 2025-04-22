package com.developing.charityapplication.presentation.event.screenEvent

import okhttp3.MultipartBody
import java.time.LocalDate
import java.time.LocalDateTime

sealed class CreatingPostEvent {

    // region -- Value Change --
    data class ContentChange(val content: String): CreatingPostEvent()
    data class StartDateChange(val startDate: LocalDateTime): CreatingPostEvent()
    data class EndDateChange(val endDate: LocalDateTime): CreatingPostEvent()
    data class ResetEndDateChange(val endDate: LocalDateTime? = null): CreatingPostEvent()
    data class FilesChange(val files: List<MultipartBody.Part>?): CreatingPostEvent()
    // endregion

    object Submit: CreatingPostEvent()
}