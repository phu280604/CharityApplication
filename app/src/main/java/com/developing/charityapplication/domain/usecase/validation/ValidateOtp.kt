package com.developing.charityapplication.domain.usecase.validation

import android.util.Log
import com.developing.charityapplication.infrastructure.abs.ValidationAbs

class ValidateOtp: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {

        val length = value.length

        if (length != 6 ) return ValidateResult(successful = false)

        return ValidateResult(successful = true)
    }

    // endregion

}