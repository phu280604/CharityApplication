package com.developing.charityapplication.presentation.event.screenEvent

import okhttp3.MultipartBody
import java.time.LocalDate
import java.time.LocalDateTime

sealed class CreatingPostEvent {

    // region -- Value Change --
    data class ContentChange(val content: String): CreatingPostEvent()
    data class StartDateChange(val startDate: LocalDate): CreatingPostEvent()
    data class EndDateChange(val endDate: LocalDate): CreatingPostEvent()
    data class ResetEndDateChange(val endDate: LocalDate? = null): CreatingPostEvent()
    data class FilesChange(val files: List<MultipartBody.Part>?): CreatingPostEvent()
    // endregion

    object Submit: CreatingPostEvent()
}