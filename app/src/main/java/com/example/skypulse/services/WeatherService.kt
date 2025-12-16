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
    private const val TAG = "WeatherService"

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

    suspend fun getWeatherData(lat: Double, lon: Double): WeatherApiResponse {
        require(lat in -90.0..90.0) { "Latitude must be between -90 and 90" }
        require(lon in -180.0..180.0) { "Longitude must be between -180 and 180" }

        return try {
            api
                .getWeatherData(
                    BuildConfig.WEATHER_API_KEY,
                    lat,
                    lon
                )
                .also {
                    Log.d(TAG, "API response received successfully for ($lat, $lon)")
                    Log.v(TAG, "Response: $it")
                }
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Request timeout for ($lat, $lon)", e)
            throw Exception("Request timeout. Please check your connection and try again.")
        } catch (e: IOException) {
            Log.e(TAG, "Network error for ($lat, $lon)", e)
            throw Exception("Network error. Please check your internet connection.")
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error fetching weather data for ($lat, $lon)", e)
            throw Exception("Unexpected error: ${e.message}")
        }
    }
}
