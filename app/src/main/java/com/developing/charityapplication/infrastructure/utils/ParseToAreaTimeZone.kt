package com.developing.charityapplication.infrastructure.utils

import android.util.Log
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object ParseToAreaTimeZone {
    // region --- Methods ---

    fun parseToVietnamDateTime(createdAt: String): LocalDateTime {
        return try {
            val instant = if (createdAt.endsWith("Z")) {
                // Trường hợp có "Z", nghĩa là UTC
                Instant.parse(createdAt)
            } else {
                // Trường hợp không có "Z", parse theo định dạng cụ thể
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
                val localDateTime = LocalDateTime.parse(createdAt, formatter)
                localDateTime.atZone(ZoneId.of("UTC")).toInstant()
            }

            // Chuyển sang múi giờ Việt Nam
            instant.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toLocalDateTime()
        } catch (e: Exception) {
            Log.e("TimeParse", "Lỗi parse ngày giờ: ${e.message}")
            LocalDateTime.now() // fallback nếu lỗi
        }
    }

    fun parseToVietnamDate(createdAt: String): LocalDate {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val localDate = LocalDate.parse(createdAt, formatter)

            localDate.atStartOfDay(ZoneId.systemDefault()).toLocalDate()
        } catch (e: DateTimeParseException) {
            Log.e("TimeParse", "Lỗi parse ngày: ${e.message}")
            LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"))
        }
    }
    // endregion
}