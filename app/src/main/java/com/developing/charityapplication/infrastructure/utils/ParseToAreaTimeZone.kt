package com.developing.charityapplication.infrastructure.utils

import android.util.Log
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ParseToAreaTimeZone {
    // region --- Methods ---

    fun parseToVietnamTime(createdAt: String): LocalDateTime {
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


    // endregion
}