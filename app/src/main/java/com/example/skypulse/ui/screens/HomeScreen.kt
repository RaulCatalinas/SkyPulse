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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.skypulse.components.common.CreateTopBar
import com.example.skypulse.components.sections.SectionHeader
import com.example.skypulse.components.weathers.CurrentWeatherCard
import com.example.skypulse.components.weathers.DailyForecastCard
import com.example.skypulse.components.weathers.HourlyForecastRow
import com.example.skypulse.components.weathers.WeatherDetailsGrid
import com.example.skypulse.mocks.MockData
import com.example.skypulse.services.LocationService
import com.example.skypulse.services.WeatherService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {}
) {
    // Usar SettingsScreen.WithDrawer para envolver el contenido con el drawer
    SettingsScreen.WithDrawer { onSettingsClick ->
        HomeScreenContent(
            onSearchClick = onSearchClick,
            onFavoritesClick = onFavoritesClick,
            onSettingsClick = onSettingsClick
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
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
    ) = LocationService.rememberLocationPermission()

    Scaffold(
        topBar = {
            CreateTopBar(
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        val context = LocalContext.current
        val locationState = remember { mutableStateOf(Pair<Double?, Double?>(null, null)) }
        val locationInfoState = remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            if (!permissionsGranted) requestPermission()
        }

        if (permissionsGranted) {
            LaunchedEffect(Unit) {
                val location = LocationService.getUserLocation(context)
                locationState.value = location
                println("Lat: ${location.first}, Lon: ${location.second}")

                if (location.first != null && location.second != null) {
                    WeatherService.getWeatherData(location.first!!, location.second!!)

                    val locationInfo = LocationService.getLocationInfo(
                        context,
                        location.first!!,
                        location.second!!
                    )
                    locationInfoState.value = "${locationInfo.city}, ${locationInfo.country}"
                    println("Location: ${locationInfo.city}, ${locationInfo.country}")
                }
            }

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
        } else {
            // TODO: Show PermissionDeniedScreen with options to request permission or search manually
        }
    }
}

