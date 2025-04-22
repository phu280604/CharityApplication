package com.developing.charityapplication.presentation.event.screenEvent

import okhttp3.MultipartBody
import java.time.LocalDate
import java.time.LocalDateTime

sealed class DonationEvent {

    // region -- Value Change --
    data class ContentChange(val content: String): DonationEvent()
    data class AmountChange(val amount: String): DonationEvent()
    // endregion

    object Submit: DonationEvent()
}