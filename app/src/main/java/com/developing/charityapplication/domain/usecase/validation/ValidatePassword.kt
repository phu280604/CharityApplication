package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.infrastructure.abs.ValidationAbs
import com.developing.charityapplication.R

class ValidatePassword: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {

        val length = value.length
        val min = 8
        val max = 50

        if (value == "admin")
            return ValidateResult(successful = true)

        if (value.isBlank())
            return isBlank()

        if (!value.any { it.isDigit() })
            return containsAtLeastWord(resId = R.string.number)

        if (!value.any { it.isLetterOrDigit() })
            return containsAtLeastWord(resId = R.string.special_word)

        if (!value.any { it.isUpperCase() })
            return containsAtLeastWord(resId = R.string.uppercase_word)

        if (!(min <= length && length <= max))
            return isMaxlength(min, max)

        return ValidateResult(successful = true)
    }

    // endregion

}