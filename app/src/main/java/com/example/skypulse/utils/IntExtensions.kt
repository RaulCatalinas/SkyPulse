package com.example.skypulse.utils

import android.content.Context
import com.example.skypulse.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Formats OpenWeatherMap API Unix timestamp to a human-readable format.
 *
 * OpenWeatherMap returns Unix timestamps (seconds since epoch) in the "dt" field.
 * API response example: { "dt": 1703347200, ... }
 *
 * @param context Context to access localized resources
 * @return Formatted string according to system language
 *
 * Output examples:
 * - English: "Today at 14:30", "Tomorrow at 09:00", "25 Dec at 18:00"
 * - Spanish: "Hoy a las 14:30", "Mañana a las 09:00", "25 dic a las 18:00"
 */
fun Int.formatWeatherDateTime(context: Context): String {
    // Convert Unix timestamp (milliseconds) → LocalDateTime
    val dateTime =
        Instant
            .ofEpochMilli(this.toLong() * 1_000L)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

    val date = dateTime.toLocalDate()
    val time = dateTime.toLocalTime()
    val locale = context.resources.configuration.locales[0]

    val today = LocalDate.now()
    val dateText = when (date) {
        today -> context.getString(R.string.today)
        today.plusDays(1) -> context.getString(R.string.tomorrow)
        today.minusDays(1) -> context.getString(R.string.yesterday)
        else -> {
            val formatter = DateTimeFormatter.ofPattern("dd MMM", locale)
            date.format(formatter)
        }
    }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)
    val timeText = time.format(timeFormatter)

    return context.getString(R.string.date_time_format, dateText, timeText)
}

fun Int.formatWeatherTime(context: Context): String {
    val locale = context.resources.configuration.locales[0]
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)

    return Instant
        .ofEpochMilli(this.toLong() * 1_000L)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()
        .format(timeFormatter)
}
