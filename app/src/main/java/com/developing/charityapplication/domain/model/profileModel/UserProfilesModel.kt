package com.developing.charityapplication.domain.model.profileModel

import java.time.LocalDateTime

data class ResponseProfilesM(
    val userId: String = "",
    val profileId: String = "",
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatarUrl: String = "",
    val location: String = "",
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val active: Boolean = false
)

data class RequestUpdateProfile(
    val firstName: String,
    val lastName: String,
    val location: String,
    val username: String
)