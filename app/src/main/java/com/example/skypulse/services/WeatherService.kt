package com.example.skypulse.services

import com.example.skypulse.constants.acceptedLanguagesCode
import com.example.skypulse.network.RetrofitClient
import com.example.skypulse.types.ApiRequest
import com.example.skypulse.types.ForecastApiResponse
import com.example.skypulse.types.WeatherApiResponse
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.Locale

object WeatherService {
    private val getWeatherMap: Map<ApiRequest, suspend (Double, Double) -> WeatherResult> =
        mapOf(
            ApiRequest.GET_WEATHER to { lat, lon ->
                WeatherResult.Weather(
                    RetrofitClient.API
                        .getWeatherData(
                            lat,
                            lon,
                            getLanguageCode()
                        )
                )
            },
            ApiRequest.GET_FORECAST to { lat, lon ->
                WeatherResult.Forecast(
                    RetrofitClient.API
                        .getForecastData(
                            lat,
                            lon,
                            getLanguageCode()
                        )
                )
            },
        )

    suspend fun getWeatherInfo(
        requestType: ApiRequest,
        lat: Double,
        lon: Double
    ): Result<WeatherResult> = executeRequest(requestType, lat, lon)

    private suspend fun executeRequest(
        requestType: ApiRequest,
        lat: Double,
        lon: Double
    ): Result<WeatherResult> {
        return runCatching {
            val request = getWeatherMap[requestType]
                ?: error("Invalid request type: $requestType")

            request(lat, lon)
        }
            .mapError()
    }


    private fun <T> Result<T>.mapError(): Result<T> {
        return fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                Result.failure(
                    when (it) {
                        is SocketTimeoutException -> WeatherException(WeatherError.Timeout)
                        is IOException -> WeatherException(WeatherError.Network)
                        else -> WeatherException(WeatherError.Unknown(it))
                    }
                )
            }
        )
    }

    private fun getLanguageCode(): String {
        val index = acceptedLanguagesCode.indexOf(Locale.getDefault().language)

        return acceptedLanguagesCode[if (index != -1) index else 0]
    }


    sealed class WeatherResult {
        data class Weather(val data: WeatherApiResponse) : WeatherResult()
        data class Forecast(val data: ForecastApiResponse) : WeatherResult()
    }

    sealed class WeatherError {
        object Timeout : WeatherError()
        object Network : WeatherError()
        data class Unknown(val cause: Throwable) : WeatherError()
    }

    class WeatherException(val error: WeatherError) : Exception()
}
