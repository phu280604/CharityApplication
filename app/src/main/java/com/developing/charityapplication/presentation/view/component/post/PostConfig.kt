package com.developing.charityapplication.presentation.view.component.post

import androidx.compose.ui.graphics.painter.Painter
import java.time.LocalDate
import java.time.LocalDateTime

data class PostConfig (
    var userbackground: Painter? = null,
    var username: String = "",
    var timeAgo: LocalDateTime? = null,
    var content: String = "",
    var maximizeImage: (String) -> Unit,
    var readOnly: Boolean = true,
    var blockDonate: Boolean = false,
    var onEdit: () -> Unit = {},
    var onDelete: () -> Unit = {},
    var fileIds: List<String> = emptyList(),
    var donationValue: String = "",
    var dateStart: LocalDate?,
    var dateEnd: LocalDate?,
    val onDonation: () -> Unit,
    var likeCount: String = "",
    val onLike: () -> Unit,
    var commentCount: String = "",
    val onComment: () -> Unit,
    var shareCount: String = "",
    val onShare: () -> Unit,
    val onSave: () -> Unit,
    val onAnalysis: () -> Unit,
    val onReport: () -> Unit
)