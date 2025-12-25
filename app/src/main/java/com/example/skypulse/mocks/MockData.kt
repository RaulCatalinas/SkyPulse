package com.example.skypulse.mocks

import com.example.skypulse.models.City
import com.example.skypulse.models.HourlyForecast

// Mock data functions
class MockData {
    companion object {
        fun getHourlyForecasts(): List<HourlyForecast> {
            return listOf(
                HourlyForecast(
                    time = "Now",
                    temperature = 22,
                    icon = "Clear",
                    description = "Clear sky",
                    precipitation = 0
                ),
                HourlyForecast(
                    time = "15:00",
                    temperature = 24,
                    icon = "Sunny",
                    description = "Sunny",
                    precipitation = 0
                ),
                HourlyForecast(
                    time = "16:00",
                    temperature = 25,
                    icon = "Partly Cloudy",
                    description = "Partly cloudy",
                    precipitation = 10
                ),
                HourlyForecast(
                    time = "17:00",
                    temperature = 23,
                    icon = "Partly Cloudy",
                    description = "Partly cloudy",
                    precipitation = 15
                ),
                HourlyForecast(
                    time = "18:00",
                    temperature = 21,
                    icon = "Cloudy",
                    description = "Few clouds",
                    precipitation = 5
                ),
                HourlyForecast(
                    time = "19:00",
                    temperature = 19,
                    icon = "Cloudy",
                    description = "Cloudy",
                    precipitation = 20
                ),
                HourlyForecast(
                    time = "20:00",
                    temperature = 18,
                    icon = "Cloudy",
                    description = "Overcast",
                    precipitation = 25
                ),
                HourlyForecast(
                    time = "21:00",
                    temperature = 17,
                    icon = "DailyForecastItemRain",
                    description = "Light rain",
                    precipitation = 40
                )
            )
        }

        fun getCities(): List<City> {
            return listOf(
                City(
                    id = "1",
                    name = "Madrid",
                    country = "Spain",
                    lat = 40.4168,
                    lon = -3.7038,
                    isFavorite = true
                ),
                City(
                    id = "2",
                    name = "Barcelona",
                    country = "Spain",
                    lat = 41.3851,
                    lon = 2.1734,
                    isFavorite = true
                ),
                City(
                    id = "3",
                    name = "London",
                    country = "United Kingdom",
                    lat = 51.5074,
                    lon = -0.1278,
                    isFavorite = false
                ),
                City(
                    id = "4",
                    name = "Paris",
                    country = "France",
                    lat = 48.8566,
                    lon = 2.3522,
                    isFavorite = true
                ),
                City(
                    id = "5",
                    name = "New York",
                    country = "United States",
                    lat = 40.7128,
                    lon = -74.0060,
                    isFavorite = false
                )
            )
        }

        fun getFavoriteCities(): List<City> {
            return getCities().filter { it.isFavorite }
        }
    }
}