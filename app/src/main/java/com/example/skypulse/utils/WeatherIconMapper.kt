package com.example.skypulse.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Maps weather conditions to Material Design icons
 */
object WeatherIconMapper {
    /**
     * Get icon based on weather condition string
     */
    fun getIconForCondition(condition: String): ImageVector {
        return when (condition.lowercase()) {
            // Clear/Sunny
            "clear", "sunny", "clear sky" -> Icons.Filled.WbSunny

            // Partly Cloudy
            "partly cloudy", "partly cloudy day", "mostly sunny" -> Icons.Filled.WbCloudy

            // Cloudy
            "cloudy", "overcast", "mostly cloudy" -> Icons.Filled.Cloud
            "few clouds" -> Icons.Filled.CloudQueue

            // DailyForecastItemRain
            "light rain", "light shower", "rain", "rainy", "moderate rain" -> Icons.Filled.WaterDrop
            "heavy rain", "shower rain", "heavy shower" -> Icons.Filled.Grain
            "drizzle", "mist rain" -> Icons.Filled.Opacity

            // Thunderstorm
            "thunderstorm", "storm", "lightning" -> Icons.Filled.Thunderstorm

            // Snow
            "snow", "snowy", "light snow", "heavy snow", "blizzard" -> Icons.Filled.AcUnit

            // Fog/Mist
            "fog", "foggy", "mist", "haze" -> Icons.Filled.Cloud

            // Wind
            "windy", "breezy" -> Icons.Filled.Air

            // Night (if you differentiate)
            "clear night" -> Icons.Filled.NightsStay
            "partly cloudy night" -> Icons.Filled.Cloud

            // Default
            else -> Icons.Filled.WbCloudy
        }
    }

    /**
     * Get icon based on weather code (OpenWeatherMap format)
     * Weather condition codes: https://openweathermap.org/weather-conditions
     */
    fun getIconForWeatherCode(code: Int): ImageVector {
        return when (code) {
            // Thunderstorm (200-232)
            in 200..232 -> Icons.Filled.Thunderstorm

            // Drizzle (300-321)
            in 300..321 -> Icons.Filled.Opacity

            // DailyForecastItemRain (500-531)
            500, 520 -> Icons.Filled.Grain // Light rain
            in 501..531 -> Icons.Filled.Water // Heavy rain

            // Snow (600-622)
            in 600..622 -> Icons.Filled.AcUnit

            // Atmosphere (fog, mist, etc.) (701-781)
            in 701..781 -> Icons.Filled.Cloud

            // Clear (800)
            800 -> Icons.Filled.WbSunny

            // Clouds (801-804)
            801 -> Icons.Filled.WbCloudy // Few clouds
            802 -> Icons.Filled.WbCloudy // Scattered clouds
            803 -> Icons.Filled.Cloud // Broken clouds
            804 -> Icons.Filled.Cloud // Overcast

            // Default
            else -> Icons.Filled.WbCloudy
        }
    }

    /**
     * Get emoji representation (for backward compatibility or fallback)
     */
    fun getEmojiForCondition(condition: String): String {
        return when (condition.lowercase()) {
            "clear", "sunny", "clear sky" -> "☀️"
            "partly cloudy", "partly cloudy day", "mostly sunny" -> "⛅"
            "cloudy", "overcast", "mostly cloudy" -> "☁️"
            "few clouds" -> "🌤️"
            "rain", "rainy", "light rain" -> "🌧️"
            "heavy rain", "shower rain" -> "⛈️"
            "drizzle" -> "🌦️"
            "thunderstorm", "storm", "lightning" -> "⛈️"
            "snow", "snowy" -> "❄️"
            "fog", "foggy", "mist" -> "🌫️"
            "windy", "breezy" -> "💨"
            "clear night" -> "🌙"
            "partly cloudy night" -> "☁️"
            else -> "⛅"
        }
    }
}

/**
 * Extension function to get icon from WeatherData
 */
fun String.toWeatherIcon(): ImageVector {
    return WeatherIconMapper.getIconForCondition(this)
}