package com.developing.charityapplication.domain.usecase.validation

import com.developing.charityapplication.infrastructure.abs.ValidationAbs
import java.time.LocalDate
import java.time.LocalDateTime

class ValidateDate: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {
        TODO("Not yet implemented")
    }

    fun execute(value: LocalDateTime?): ValidateResult {

        if (value == null)
            return isDateNull()

        return ValidateResult(successful = true)
    }

    // endregion

}