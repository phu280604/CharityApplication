package com.developing.charityapplication.infrastructure.utils

import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object ConverterData {
    fun <T> toJson(data: T): String{
        return Gson().toJson(data)
    }

    inline fun <reified T> fromJson(data: String): T {
        return Gson().fromJson(data, T::class.java)
    }

    fun convertCalendarToLocalDate(calendar: Calendar): LocalDate {
        // Định dạng ngày tháng từ Calendar
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = simpleDateFormat.format(calendar.time)

        // Chuyển đổi chuỗi thành LocalDate với định dạng dd/MM/yyyy
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(dateString, formatter)
    }


    fun convertLocalDateToCalendar(localDate: LocalDate): Calendar {
        // Format LocalDate thành chuỗi với đúng định dạng khớp
        val dateString = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        // Parse lại với cùng định dạng
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = simpleDateFormat.parse(dateString)

        // Chuyển sang Calendar
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return calendar
    }

}