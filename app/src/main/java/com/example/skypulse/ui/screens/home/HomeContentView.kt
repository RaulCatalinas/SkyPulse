package com.example.skypulse.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.skypulse.components.common.CreateTopBar
import com.example.skypulse.services.LocationService
import com.example.skypulse.services.WeatherService
import com.example.skypulse.ui.screens.states.HomeScreenState
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContentView(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val (permissionsGranted, requestPermission) = LocationService.rememberLocationPermission()
    var screenState by remember { mutableStateOf<HomeScreenState>(HomeScreenState.Loading) }

    // Request permissions if not granted
    LaunchedEffect(Unit) {
        if (!permissionsGranted) requestPermission()
    }

    // Load data when we have permissions
    LaunchedEffect(permissionsGranted) {
        if (!permissionsGranted) {
            screenState = HomeScreenState.PermissionDenied
            return@LaunchedEffect
        }

        screenState = HomeScreenState.Loading

        try {
            val locationResult = LocationService.getUserLocation(context)

            if (locationResult.isFailure) {
                screenState = HomeScreenState.Error("Could not obtain location")
                return@LaunchedEffect
            }

            val location = locationResult.getOrThrow()

            supervisorScope {
                val weatherDataDeferred = async {
                    runCatching {
                        WeatherService.getWeatherData(location.latitude, location.longitude)
                    }
                }
                val locationInfoDeferred = async {
                    runCatching {
                        LocationService.getLocationInfo(
                            context,
                            location.latitude,
                            location.longitude
                        )
                    }
                }

                val weatherResult = weatherDataDeferred.await()
                val locationInfoResult = locationInfoDeferred.await()

                when {
                    weatherResult.isFailure -> {
                        screenState = HomeScreenState.Error(
                            "Weather data failed: ${
                                weatherResult.exceptionOrNull()?.message
                            }"
                        )
                    }

                    locationInfoResult.isFailure -> {
                        screenState = HomeScreenState.Error(
                            "Location info failed: ${
                                locationInfoResult
                                    .exceptionOrNull()
                                    ?.message
                            }"
                        )
                    }

                    else -> {
                        val weatherData = weatherResult.getOrThrow()
                        val locationInfo = locationInfoResult.getOrThrow()

                        screenState = HomeScreenState.Success(
                            weatherData = weatherData,
                            locationInfo = "${locationInfo.city}, ${locationInfo.country}"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            screenState = HomeScreenState.Error(e.message ?: "Unknown error")
        }
    }

    Scaffold(
        topBar = {
            CreateTopBar(
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        when (val state = screenState) {
            is HomeScreenState.Loading -> HomeLoadingView(paddingValues)
            is HomeScreenState.PermissionDenied -> HomePermissionDeniedView(paddingValues)
            is HomeScreenState.Error -> HomeErrorView(state.message, paddingValues)
            is HomeScreenState.Success -> HomeSuccessView(state, paddingValues)
        }
    }
}