package com.example.skypulse.services

import com.example.skypulse.constants.API_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
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

    suspend fun getWeatherData() {
        val response = client.get(API_URL)

        println(response)
    }

    fun closeClient() {
        client.close()
    }
}
