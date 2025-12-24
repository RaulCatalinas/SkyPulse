package com.example.skypulse.services

import com.example.skypulse.BuildConfig
import com.example.skypulse.constants.BASE_API_URL
import com.example.skypulse.constants.METRIC_UNIT_OF_MEASUREMENT
import com.example.skypulse.constants.acceptedLanguagesCode
import com.example.skypulse.types.Api
import com.example.skypulse.types.ApiRequest
import com.example.skypulse.types.ForecastApiResponse
import com.example.skypulse.types.WeatherApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.Locale
import java.util.concurrent.TimeUnit

object WeatherService {
    private const val API_KEY = BuildConfig.WEATHER_API_KEY

    private val loggingInterceptor =
        HttpLoggingInterceptor()
            .apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
            }

    private val okHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                addInterceptor(loggingInterceptor)
                connectTimeout(10, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
            }
            .build()

    private val api =
        Retrofit
            .Builder()
            .apply {
                baseUrl(BASE_API_URL)
                client(okHttpClient)
                addConverterFactory(GsonConverterFactory.create())
            }
            .build()
            .create(Api::class.java)

    private val getWeatherMap: Map<ApiRequest, suspend (Double, Double) -> WeatherResult> =
        mapOf(
            ApiRequest.GET_WEATHER to { lat, lon ->
                WeatherResult.Weather(
                    api
                        .getWeatherData(
                            API_KEY,
                            lat,
                            lon,
                            METRIC_UNIT_OF_MEASUREMENT,
                            getLanguageCode()
                        )
                )
            },
            ApiRequest.GET_FORECAST to { lat, lon ->
                WeatherResult.Forecast(
                    api
                        .getForecastData(
                            API_KEY,
                            lat,
                            lon,
                            METRIC_UNIT_OF_MEASUREMENT,
                            getLanguageCode()
                        )
                )
            }
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
