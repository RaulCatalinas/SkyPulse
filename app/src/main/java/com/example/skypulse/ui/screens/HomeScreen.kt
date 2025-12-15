package com.example.skypulse.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.skypulse.components.common.CreateTopBar
import com.example.skypulse.mocks.MockData
import com.example.skypulse.services.LocationService
import com.example.skypulse.services.WeatherService
import com.example.skypulse.types.WeatherApiResponse
import com.example.skypulse.ui.renders.RenderWeatherData

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
        val weatherDataState = remember { mutableStateOf<WeatherApiResponse?>(null) }
        val isLoadingState = remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            if (!permissionsGranted) requestPermission()
        }

        if (permissionsGranted) {
            LaunchedEffect(Unit) {
                isLoadingState.value = true
                val location = LocationService.getUserLocation(context)
                locationState.value = location

                if (location.first == null || location.second == null) {
                    Log.e("HomeScreen", "Error obtaining the user's geolocation")
                    isLoadingState.value = false
                    return@LaunchedEffect
                }

                weatherDataState.value = WeatherService.getWeatherData(
                    location.first!!,
                    location.second!!
                )

                val locationInfo = LocationService.getLocationInfo(
                    context,
                    location.first!!,
                    location.second!!
                )

                locationInfoState.value = "${locationInfo.city}, ${locationInfo.country}"
                isLoadingState.value = false
            }

            if (isLoadingState.value) {
                // Show loading indicator
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                RenderWeatherData(
                    dailyForecasts,
                    hourlyForecasts,
                    weatherDataState.value!!,
                    locationInfoState.value!!,
                    paddingValues
                )
            }
        } else {
            // TODO: Show PermissionDeniedScreen with options to request permission or search manually
        }
    }
}