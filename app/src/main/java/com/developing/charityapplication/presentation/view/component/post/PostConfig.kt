package com.developing.charityapplication.presentation.view.component.post

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDateTime

data class PostConfig (
    var userbackground: Painter? = null,
    var username: String = "",
    var timeAgo: LocalDateTime? = null,
    var content: String = "",
    var fileIds: List<String> = emptyList(),
    var donationValue: String = "",
    var dateRange: String = "",
    var likeCount: String = "",
    var commentCount: String = "",
    var shareCount: String = ""
)