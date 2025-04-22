package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.infrastructure.abs.ValidationAbs
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.ConverterData.toVndCurrencyCompact

class ValidateAmount: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {

        if (value.isBlank())
            return isBlank()

        return ValidateResult(successful = true)
    }

    fun execute(value: Long, minValue: Long): ValidateResult {

        if (value < minValue)
            return ValidateResult(
                successful = false,
                errorMessage = getStringError(R.string.amount_over) + " " + minValue.toVndCurrencyCompact()
            )

        return ValidateResult(successful = true)
    }

    // endregion

}