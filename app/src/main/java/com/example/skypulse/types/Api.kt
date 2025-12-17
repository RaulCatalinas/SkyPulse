package com.example.skypulse.types

import retrofit2.http.GET
import retrofit2.http.Query


interface Api {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("appid") appId: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String
    ): WeatherApiResponse
}