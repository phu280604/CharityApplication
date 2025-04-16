package com.developing.charityapplication.domain.model.postModel

data class CommentM(
    val content: String,
    val createdAt: String,
    val profileId: String,
    val updatedAt: String
)

data class ReactionM(
    val profileId: String,
    val reactionType: String
)