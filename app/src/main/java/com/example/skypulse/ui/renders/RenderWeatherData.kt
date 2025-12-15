package com.example.skypulse.ui.renders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.components.sections.SectionHeader
import com.example.skypulse.components.weathers.CurrentWeatherCard
import com.example.skypulse.components.weathers.DailyForecastCard
import com.example.skypulse.components.weathers.HourlyForecastRow
import com.example.skypulse.components.weathers.WeatherDetailsGrid
import com.example.skypulse.models.DailyForecast
import com.example.skypulse.models.HourlyForecast
import com.example.skypulse.types.WeatherApiResponse

@Composable
fun RenderWeatherData(
    dailyForecasts: List<DailyForecast>,
    hourlyForecasts: List<HourlyForecast>,
    weatherData: WeatherApiResponse,
    location: String,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Current Weather Card
        item {
            CurrentWeatherCard(
                weatherData = weatherData,
                location = location,
                onClick = { /* Navigate to details */ }
            )
        }

        // Weather Details Grid
        item {
            WeatherDetailsGrid(weatherData = weatherData)
        }

        // Hourly Forecast Section
        item {
            SectionHeader(title = "Hourly Forecast")
            HourlyForecastRow(
                forecasts = hourlyForecasts,
                onItemClick = { forecast ->
                    // Handle hourly forecast click
                    println("Clicked on ${forecast.time}")
                }
            )
        }

        // Daily Forecast Section
        item {
            SectionHeader(title = "7-Day Forecast")
        }

        // Daily Forecast Items
        items(items = dailyForecasts) { forecast ->
            DailyForecastCard(
                forecast = forecast,
                onClick = {
                    // Handle daily forecast click
                    println("Clicked on ${forecast.day}")
                }
            )
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}