package com.developing.charityapplication.domain.usecase.validation

data class ValidateResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
