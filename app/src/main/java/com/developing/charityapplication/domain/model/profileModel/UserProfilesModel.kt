package com.developing.charityapplication.domain.model.profileModel

import android.os.Parcelable
import kotlinx.serialization.Serializable
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

@Serializable
data class RequestUpdateProfileM(
    var firstName: String = "",
    var lastName: String = "",
    var location: String = "",
    var username: String = ""
)