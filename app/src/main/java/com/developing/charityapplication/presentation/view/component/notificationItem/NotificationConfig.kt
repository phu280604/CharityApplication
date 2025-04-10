package com.developing.charityapplication.presentation.view.component.notificationItem

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class NotificationConfig(
    val username: String = "",
    val background: Int = -1,
    val content: String = "",
    val timeStamp: String = "",
    val isRead: MutableState<Boolean> = mutableStateOf(false),
    val onClick: () -> Unit = {}
)