package com.developing.charityapplication.domain.model

data class ResponseModel<T>(
    var code: Int = 0,
    var message: String = "",
    var result: T? = null
)