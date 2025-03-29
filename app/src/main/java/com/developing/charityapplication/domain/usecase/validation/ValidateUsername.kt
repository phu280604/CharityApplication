package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.infrastructure.abs.ValidationAbs

class ValidateUsername: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {

        val length = value.length
        val min = 3
        val max = 20

        if (value.isBlank())
            return isBlank()

        if (!(min <= length && length <= max))
            return isMaxlength(min, max)

        return ValidateResult(successful = true)
    }

    // endregion

}