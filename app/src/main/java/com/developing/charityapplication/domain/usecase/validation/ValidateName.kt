package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.infrastructure.abs.ValidationAbs

class ValidateName: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {

        val length = value.length
        val min = 1
        val max = 15

        if (value.isBlank())
            return isBlank()

        if (!value.any { it.isLetter() })
            return containsWord()

        if (!(min <= length && length <= max))
            return isMaxlength(min, max)

        return ValidateResult(successful = true)
    }

    // endregion

}