package com.developing.charityapplication.presentation.state.screenState

import android.content.Context
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

data class CreatingPostState(
    val content: String = "",
    val contentError: String? = null,
    val startDate: String = "",
    val startDateError: String? = null,
    val endDate: String = "",
    val endDateError: String? = null,
    val files: List<MultipartBody.Part>? = null,
    val filesError: String? = null,
)

