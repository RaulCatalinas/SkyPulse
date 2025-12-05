package com.example.skypulse.services

import android.util.Log
import androidx.core.net.toUri
import com.example.skypulse.constants.API_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

object WeatherService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
    }

    suspend fun getWeatherData(lat: Double, lon: Double): Result<String> {
        return try {
            // Validate input parameters
            if (lat < -90 || lat > 90) {
                Log.e("WeatherService", "Invalid latitude: $lat")
                return Result.failure(IllegalArgumentException("Latitude must be between -90 and 90"))
            }
            if (lon < -180 || lon > 180) {
                Log.e("WeatherService", "Invalid longitude: $lon")
                return Result.failure(IllegalArgumentException("Longitude must be between -180 and 180"))
            }

            // Build URL
            val uriBuilder = API_URL.toUri().buildUpon()
            uriBuilder.appendQueryParameter("lat", lat.toString())
            uriBuilder.appendQueryParameter("lon", lon.toString())
            val url = uriBuilder.build().toString()

            Log.d("WeatherService", "Fetching weather data from: $url")

            // Make API request
            val response = client.get(url)

            // Validate HTTP status
            if (response.status.value !in 200..299) {
                val errorBody = response.bodyAsText()
                Log.e(
                    "WeatherService",
                    "API request failed with status: ${response.status}. Body: $errorBody"
                )
                return Result.failure(
                    Exception("API request failed: ${response.status.value} - ${response.status.description}")
                )
            }

            val responseBody = response.bodyAsText()
            Log.d("WeatherService", "API response received successfully")
            Log.v("WeatherService", "Response body: $responseBody")

            Result.success(responseBody)

        } catch (e: SocketTimeoutException) {
            Log.e("WeatherService", "Request timeout: ${e.message}", e)
            Result.failure(Exception("Request timeout. Please check your connection and try again."))
        } catch (e: SocketException) {
            Log.e("WeatherService", "Network error: ${e.message}", e)
            Result.failure(Exception("Network error. Please check your internet connection."))
        } catch (e: IOException) {
            Log.e("WeatherService", "IO error: ${e.message}", e)
            Result.failure(Exception("Connection error: ${e.message}"))
        } catch (e: IllegalArgumentException) {
            Log.e("WeatherService", "Invalid argument: ${e.message}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WeatherService", "Unexpected error fetching weather data: ${e.message}", e)
            Result.failure(Exception("Unexpected error: ${e.message}"))
        }
    }

    fun closeClient() {
        client.close()
    }
}
