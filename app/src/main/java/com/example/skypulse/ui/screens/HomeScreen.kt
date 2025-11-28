package com.example.skypulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.components.common.CreateTopBar
import com.example.skypulse.components.sections.SectionHeader
import com.example.skypulse.components.weathers.CurrentWeatherCard
import com.example.skypulse.components.weathers.DailyForecastCard
import com.example.skypulse.components.weathers.HourlyForecastRow
import com.example.skypulse.components.weathers.WeatherDetailsGrid
import com.example.skypulse.mocks.MockData
import com.example.skypulse.permissions.rememberLocationPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    // Mock data from MockData utility - will be replaced with ViewModel later
    val weatherData = MockData.getWeatherData()
    val hourlyForecasts = MockData.getHourlyForecasts()
    val dailyForecasts = MockData.getDailyForecasts()

    val (
        permissionsGranted,
        requestPermission
    ) = rememberLocationPermission()

    Scaffold(
        topBar = {
            CreateTopBar(
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        LaunchedEffect(Unit) {
            if (!permissionsGranted) requestPermission()
        }

        if (permissionsGranted) {
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

                // Daily Forecast Items - CORREGIDO
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
        } else {
            // TODO: Show PermissionDeniedScreen with options to request permission or search manually
        }
    }
}

