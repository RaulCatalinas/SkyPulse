package com.example.skypulse.ui.screens.states

import com.example.skypulse.types.WeatherApiResponse

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    object PermissionDenied : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
    data class Success(
        val weatherData: WeatherApiResponse,
        val locationInfo: String
    ) : HomeScreenState()
}