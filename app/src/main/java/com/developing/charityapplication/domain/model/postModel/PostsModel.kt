package com.developing.charityapplication.domain.model.postModel

import com.developing.charityapplication.domain.model.utilitiesModel.PointM
import java.time.LocalDateTime
import java.util.Date

data class RequestPostContentM(
    val profileId: String,
    val content: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val privacy: String = "PUBLIC"
)

data class ResponsePostM(
    val profileId: String,
    val content: String,
    val fileIds: List<String>,
    val reactions: List<ReactionM>,
    val tags: List<String>,
    val privacy: String,
    val point: PointM,
    val comments: List<CommentM>,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
)