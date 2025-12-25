package com.example.skypulse.repositories

import com.example.skypulse.domain.mappers.toUiModel
import com.example.skypulse.domain.models.CurrentWeather
import com.example.skypulse.domain.models.DailyForecastWeather
import com.example.skypulse.services.WeatherService
import com.example.skypulse.types.ApiRequest

/**
 * Repository layer that abstracts weather data sources.
 *
 * This layer:
 * - Decouples UI from the service implementation
 * - Makes it easy to switch APIs in the future
 * - Provides a clean interface for the ViewModel
 * - Handles data transformation and rounding
 */
object WeatherRepository {

    /**
     * Fetches current weather data for given coordinates.
     *
     * @param lat Latitude
     * @param lon Longitude
     * @return Result containing CurrentWeather or error
     */
    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Result<CurrentWeather> {
        return WeatherService
            .getWeatherInfo(ApiRequest.GET_WEATHER, lat, lon)
            .mapCatching {
                when (it) {
                    is WeatherService.WeatherResult.Weather -> it.data.toUiModel()
                    else -> error("Unexpected result type")
                }
            }
    }

    /**
     * Fetches 5-day forecast data for given coordinates.
     *
     * @param lat Latitude
     * @param lon Longitude
     * @return Result containing DailyForecastWeather or error
     */
    suspend fun getForecast(
        lat: Double,
        lon: Double
    ): Result<DailyForecastWeather> {
        return WeatherService
            .getWeatherInfo(ApiRequest.GET_FORECAST, lat, lon)
            .mapCatching {
                when (it) {
                    is WeatherService.WeatherResult.Forecast -> it.data.toUiModel()
                    else -> error("Unexpected result type")
                }
            }
    }
}