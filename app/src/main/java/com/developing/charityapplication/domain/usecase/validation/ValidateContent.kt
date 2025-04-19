package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.infrastructure.abs.ValidationAbs

class ValidateContent: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {

        if (value.isBlank())
            return isBlank()

        return ValidateResult(successful = true)
    }

    // endregion

}