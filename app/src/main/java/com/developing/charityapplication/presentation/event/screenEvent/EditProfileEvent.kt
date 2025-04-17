package com.developing.charityapplication.presentation.event.screenEvent

import okhttp3.MultipartBody

sealed class EditProfileEvent {

    // region -- Value Change --
    data class FirstNameChange(val firstName: String): EditProfileEvent()
    data class LastNameChange(val lastName: String): EditProfileEvent()
    data class UsernameChange(val username: String): EditProfileEvent()
    data class LocationChange(val location: String): EditProfileEvent()
    data class AvatarChange(val avatar: MultipartBody.Part?): EditProfileEvent()
    // endregion

    object Submit: EditProfileEvent()
}