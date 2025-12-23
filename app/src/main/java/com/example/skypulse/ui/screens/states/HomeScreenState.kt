package com.example.skypulse.ui.screens.states

import com.example.skypulse.types.ForecastApiResponse
import com.example.skypulse.types.WeatherApiResponse
import com.example.skypulse.ui.mappers.WeatherUiError

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    object PermissionDenied : HomeScreenState()
    data class Error(val error: WeatherUiError) : HomeScreenState()
    data class Success(
        val weatherData: WeatherApiResponse,
        val forecastData: ForecastApiResponse,
        val locationInfo: String
    ) : HomeScreenState()
}