package com.example.skypulse.ui.mappers

import com.example.skypulse.services.WeatherService

sealed class WeatherUiError {
    object Timeout : WeatherUiError()
    object Network : WeatherUiError()
    object Unknown : WeatherUiError()
}

fun <T> Result<T>.toUiError(): WeatherUiError {
    return when ((exceptionOrNull() as? WeatherService.WeatherException)?.error) {
        WeatherService.WeatherError.Timeout -> WeatherUiError.Timeout
        WeatherService.WeatherError.Network -> WeatherUiError.Network
        else -> WeatherUiError.Unknown
    }
}

