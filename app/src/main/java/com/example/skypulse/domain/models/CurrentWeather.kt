package com.example.skypulse.domain.models

data class CurrentWeather(
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: MainCurrentWeather,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val timestamp: Long,
    val sys: Sys,
    val timezone: Int,
    val cityId: Int,
    val cityName: String,
    val statusCode: Int
)

data class Coordinates(
    val longitude: Double,
    val latitude: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class MainCurrentWeather(
    val temperature: Int,
    val feelsLike: Int,
    val tempMin: Int,
    val tempMax: Int,
    val pressure: Int,
    val humidity: Int,
    val seaLevel: Int? = null,
    val groundLevel: Int? = null
)

data class Wind(
    val speed: Double,
    val degrees: Int,
    val gust: Double? = null
)

data class Clouds(
    val cloudiness: Int
)

data class Sys(
    val type: Int? = null,
    val id: Int? = null,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
