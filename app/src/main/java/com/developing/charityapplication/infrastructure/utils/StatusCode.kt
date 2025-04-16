package com.developing.charityapplication.infrastructure.utils

import android.util.Log
import com.developing.charityapplication.HeartBellApplication
import com.developing.charityapplication.R

enum class StatusCode(val code: Int, val statusResId: Int) {

    // region -- Values --
    SUCCESS(1000, R.string.status_1000),
    STRANGE_DEVICE(1, R.string.status_1),
    INVALID_KEY_PROVIDED(1001, R.string.status_1001),
    USER_ALREADY_EXISTS(1002, R.string.status_1002),
    OTP_EXPIRED(1022, R.string.status_1022),
    UNKNOWN(9999, R.string.status_9999);
    // endregion

    companion object {
        // region --- Methods ---

        fun fromCode(code: Int): StatusCode {
            currentCode = StatusCode.entries.find { it.code == code }?.code ?: UNKNOWN.code
            return StatusCode.entries.find { it.code == code } ?: UNKNOWN
        }

        fun fromStatusResId(statusId: Int): String{
            return try{
                HeartBellApplication.getAppContext().getString(statusId)
            }
            catch (ex: Exception){
                Log.d("Status_not_found", ex.toString())
                return ""
            }
        }

        // endregion

        // region --- Fields ---

        var currentCode: Int = 0
            get() = field
            private set(value) { field = value }

        // endregion
    }

}