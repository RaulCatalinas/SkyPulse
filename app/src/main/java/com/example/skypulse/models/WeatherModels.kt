package com.example.skypulse.models

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