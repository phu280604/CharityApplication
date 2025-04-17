package com.developing.charityapplication.presentation.state.screenState

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

data class EditProfileState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val username: String = "",
    val usernameError: String? = null,
    val location: String = "",
    val locationError: String? = null,
    val avatar: MultipartBody.Part? = null,
    val avatarError: String? = null,
)

