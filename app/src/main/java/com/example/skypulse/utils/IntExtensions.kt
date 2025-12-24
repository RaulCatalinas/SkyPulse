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
    // Convert Unix timestamp (seconds) to LocalDateTime
    val dateTime = Instant.ofEpochSecond(this.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val date = dateTime.toLocalDate()
    val time = dateTime.toLocalTime()

    val locale = context.resources.configuration.locales[0]

    // Determine if it's today, tomorrow or yesterday
    val today = LocalDate.now()
    val dateText = when (date) {
        today -> context.getString(R.string.today)
        today.plusDays(1) -> context.getString(R.string.tomorrow)
        today.minusDays(1) -> context.getString(R.string.yesterday)
        else -> {
            // Format: "25 Dec" or "25 dic"
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM", locale)
            date.format(dateFormatter)
        }
    }

    // Format time according to locale
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", locale)
    val timeText = time.format(timeFormatter)

    return context.getString(R.string.date_time_format, dateText, timeText)
}