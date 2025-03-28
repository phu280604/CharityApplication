package com.developing.charityapplication.infrastructure.utils

import androidx.compose.ui.res.stringResource
import com.developing.charityapplication.R

object Checker {

    // region --- Methods ---

    fun containsBlank(value: String) : Int{
        if (value.isEmpty() || value.isBlank())
            return R.string.error_empty_field

        return 0
    }

    fun outOfRange(
        value: String,
        max: Int,
        min: Int = 1) : Int{
        val length = value.length
        if (min <= length  && length <= max )
            return R.string.error_max_length_field

        return 0
    }

    fun containsDigit(value: String) : Int{
        if(value.any { it.isDigit() }) return R.string.number
        return 0
    }

    fun containsSpecialChar(value: String) : Int{
        val regex = Regex("[^a-zA-Z0-9]")
        if(regex.containsMatchIn(value)) return R.string.special_word
        return 0
    }

    fun containsOnlyLetters(input: String): Int {
        val regex = Regex("^[a-zA-Z]+$")
        if(regex.matches(input)) return R.string.word
        return 0
    }

    // endregion

}