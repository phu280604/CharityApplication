package com.developing.charityapplication.presentation.state.screenState

import androidx.compose.ui.graphics.painter.Painter

data class ShareDonationState(
    val targetAvatar: Painter? = null,
    val targetName: String = "",
    val targetPostId: String = "",
    val senderId: String = "",
    val senderName: String = ""
)