package com.developing.charityapplication.infrastructure.utils

import android.util.Log
import com.developing.charityapplication.HeartBellApplication
import com.developing.charityapplication.R

enum class StatusCode(val code: Int, val statusResId: Int) {

    // region -- Values --
    OK(200, R.string.status_200),
    CREATED(201, R.string.status_201),
    ACCEPTED(202, R.string.status_202),
    NO_CONTENT(204, R.string.status_204),

    BAD_REQUEST(400, R.string.status_400),
    UNAUTHORIZED(401, R.string.status_401),
    FORBIDDEN(403, R.string.status_403),
    NOT_FOUND(404, R.string.status_404),
    METHOD_NOT_ALLOWED(405, R.string.status_405),
    REQUEST_TIMEOUT(408, R.string.status_408),
    CONFLICT(409, R.string.status_409),
    TOO_MANY_REQUESTS(429, R.string.status_429),

    INTERNAL_SERVER_ERROR(500, R.string.status_500),
    NOT_IMPLEMENTED(501, R.string.status_501),
    BAD_GATEWAY(502, R.string.status_502),
    SERVICE_UNAVAILABLE(503, R.string.status_503),
    GATEWAY_TIMEOUT(504, R.string.status_504),

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