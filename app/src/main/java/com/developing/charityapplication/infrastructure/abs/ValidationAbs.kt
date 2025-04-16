package com.developing.charityapplication.infrastructure.abs

import com.developing.charityapplication.HeartBellApplication
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.usecase.validation.ValidateResult

abstract class ValidationAbs {

    // region --- Abstract ---

    abstract fun execute(value: String): ValidateResult

    // endregion

    // region --- Methods ---

    fun getStringError(resId: Int): String{
        return HeartBellApplication.getAppContext().getString(resId)
    }

    fun isBlank(): ValidateResult{
        return ValidateResult(
            successful =  false,
            errorMessage = getStringError(R.string.error_empty_field)
        )
    }

    fun containsAtLeastWord(resId: Int, count: Int = 1): ValidateResult{
        val string = getStringError(R.string.error_contain_at_least_field) + " ${count} " +
                getStringError(resId)

        return ValidateResult(
            successful =  false,
            errorMessage = string
        )
    }

    fun containsWord(): ValidateResult{
        val string = getStringError(R.string.error_contain_field) + " " +
                getStringError(R.string.word)

        return ValidateResult(
            successful =  false,
            errorMessage = string
        )
    }

    fun containsNumber(): ValidateResult{
        val string = getStringError(R.string.error_contain_field) + " " +
                getStringError(R.string.number)

        return ValidateResult(
            successful =  false,
            errorMessage = string
        )
    }

    fun containsSpecialWord(): ValidateResult{
        val string = getStringError(R.string.error_contain_field) + " " +
                getStringError(R.string.special_word)

        return ValidateResult(
            successful =  false,
            errorMessage = string
        )
    }

    fun isMaxlength(min: Int = 1, max: Int = Int.MAX_VALUE): ValidateResult{
        val string = getStringError(R.string.error_max_length_field) +
                " ${min} " +
                getStringError(R.string.to_number) +
                " ${max} " +
                getStringError(R.string.word)

        return ValidateResult(
            successful =  false,
            errorMessage = string
        )
    }

    // endregion

}