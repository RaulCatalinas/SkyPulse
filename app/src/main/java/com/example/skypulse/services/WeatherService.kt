package com.example.skypulse.services

import android.util.Log
import com.example.skypulse.BuildConfig
import com.example.skypulse.constants.BASE_API_URL
import com.example.skypulse.types.Api
import com.example.skypulse.types.WeatherApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object WeatherService {
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

    suspend fun getWeatherData(lat: Double, lon: Double): WeatherApiResponse? {
        require(lat in -90.0..90.0) { "Latitude must be between -90 and 90" }
        require(lon in -180.0..180.0) { "Longitude must be between -180 and 180" }

        return try {
            val response = api.getWeatherData(
                BuildConfig.WEATHER_API_KEY,
                lat,
                lon
            )

            Log.d("WeatherService", "API response received successfully")
            Log.v("WeatherService", "Response body: $response")

            response
        } catch (e: SocketTimeoutException) {
            Log.e("WeatherService", "Request timeout", e)
            throw Exception("Request timeout. Please check your connection and try again.")
        } catch (e: IOException) {
            Log.e("WeatherService", "Network error", e)
            throw Exception("Network error. Please check your internet connection.")
        } catch (e: Exception) {
            Log.e("WeatherService", "Unexpected error fetching weather data", e)
            throw Exception("Unexpected error: ${e.message}")
        }
    }
}
