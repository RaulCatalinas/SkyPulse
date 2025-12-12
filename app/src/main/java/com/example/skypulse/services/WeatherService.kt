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
import java.net.SocketException
import java.net.SocketTimeoutException

object WeatherService {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val api =
        Retrofit
            .Builder()
            .baseUrl(BASE_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

    suspend fun getWeatherData(lat: Double, lon: Double): WeatherApiResponse? {
        // Validate input parameters
        if (lat < -90 || lat > 90) {
            Log.e("WeatherService", "Invalid latitude: $lat")
            throw IllegalArgumentException("Latitude must be between -90 and 90")
        }
        if (lon < -180 || lon > 180) {
            Log.e("WeatherService", "Invalid longitude: $lon")
            throw IllegalArgumentException("Longitude must be between -180 and 180")
        }

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
            Log.e("WeatherService", "Request timeout: ${e.message}", e)
            throw Exception("Request timeout. Please check your connection and try again.")
        } catch (e: SocketException) {
            Log.e("WeatherService", "Network error: ${e.message}", e)
            throw Exception("Network error. Please check your internet connection.")
        } catch (e: IOException) {
            Log.e("WeatherService", "IO error: ${e.message}", e)
            throw Exception("Connection error: ${e.message}")
        } catch (e: Exception) {
            Log.e("WeatherService", "Unexpected error fetching weather data: ${e.message}", e)
            throw Exception("Unexpected error: ${e.message}")
        }
    }
}
