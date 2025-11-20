package com.example.skypulse.models

/**
 * Main weather data model for current weather conditions
 */
data class WeatherData(
    val location: String,
    val temperature: Int,
    val description: String,
    val feelsLike: Int,
    val humidity: Int,
    val windSpeed: Int,
    val uvIndex: Int,
    val pressure: Int? = null,
    val visibility: Int? = null,
    val sunrise: String? = null,
    val sunset: String? = null
)

/**
 * Hourly forecast model
 */
data class HourlyForecast(
    val time: String,
    val temperature: Int,
    val icon: String,
    val description: String? = null,
    val precipitation: Int? = null
)

/**
 * Daily forecast model
 */
data class DailyForecast(
    val day: String,
    val date: String? = null,
    val maxTemp: Int,
    val minTemp: Int,
    val description: String,
    val icon: String? = null,
    val precipitation: Int? = null
)

/**
 * City model for favorites and search
 */
data class City(
    val id: String,
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val isFavorite: Boolean = false
)