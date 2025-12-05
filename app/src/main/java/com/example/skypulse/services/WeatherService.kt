package com.example.skypulse.services

import androidx.core.net.toUri
import com.example.skypulse.constants.API_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

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

    suspend fun getWeatherData(lat: Double, lon: Double) {
        val uriBuilder = API_URL.toUri().buildUpon()

        uriBuilder.appendQueryParameter("lat", lat.toString())
        uriBuilder.appendQueryParameter("lon", lon.toString())

        val response = client.get(uriBuilder.build().toString())

        println("API response body: ${response.bodyAsText()}")
    }

    fun closeClient() {
        client.close()
    }
}
