package com.developing.charityapplication.domain.model.utilitiesModel

data class ResponseM<T>(
    var code: Int = 0,
    var message: String = "",
    var result: T? = null
)