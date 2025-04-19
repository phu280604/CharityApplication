package com.developing.charityapplication.domain.model.postModel

import com.developing.charityapplication.domain.model.utilitiesModel.PointM
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class RequestPostContentM(
    val profileId: String,
    val content: String,
    val startDate: LocalDate?,
    val endDate: LocalDate?,
    val privacy: String = "PUBLIC"
)

data class ResponsePostM(
    val id: String,
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

data class ResponsePostsByProfileId(
    val currentPage: Int,
    val totalPages: Int,
    val pageSize: Int,
    val totalElements: Int,
    val `data`: List<ResponsePostM>,
)