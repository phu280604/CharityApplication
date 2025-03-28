package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.HeartBellApplication
import com.developing.charityapplication.infrastructure.abs.ValidationAbs
import com.developing.charityapplication.R

class ValidateRepeatedPassword: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {
        TODO("Not yet implemented")
    }

    // endregion

    // region --- Methods ---
    fun execute(value: String, repeatedValue: String): ValidateResult {

        if (value.isBlank())
            return isBlank()

        if (value != repeatedValue)
            return ValidateResult(
                successful = false,
                errorMessage = HeartBellApplication.getAppContext().getString(R.string.error_not_match_field)
            )

        return ValidateResult(successful = true)
    }
    // endregion

}