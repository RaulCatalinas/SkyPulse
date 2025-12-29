package com.example.skypulse.network

import com.example.skypulse.types.ForecastApiResponse
import com.example.skypulse.types.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ): WeatherApiResponse

    @GET("forecast")
    suspend fun getForecastData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String
    ): ForecastApiResponse
}