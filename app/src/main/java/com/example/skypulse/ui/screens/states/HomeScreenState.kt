package com.example.skypulse.ui.screens.states

import com.example.skypulse.domain.models.CurrentWeather
import com.example.skypulse.domain.models.DailyForecastWeather
import com.example.skypulse.ui.mappers.WeatherUiError

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    object PermissionDenied : HomeScreenState()
    data class Error(val error: WeatherUiError) : HomeScreenState()
    data class Success(
        val weatherData: CurrentWeather,
        val forecastData: DailyForecastWeather,
        val locationInfo: String
    ) : HomeScreenState()
}