package com.developing.charityapplication.domain.usecase.validation

import android.util.Patterns
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.abs.ValidationAbs

class ValidateEmail: ValidationAbs() {

    // region --- Override ---

    override fun execute(value: String): ValidateResult {
        if(value.isBlank())
            return isBlank()

        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches())
            return ValidateResult(
                successful =  false,
                errorMessage = getStringError(R.string.error_email_field)
            )

        return ValidateResult(successful = true)
    }

    // endregion

}