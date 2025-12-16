package com.example.skypulse.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.components.common.CreateTopBar
import com.example.skypulse.mocks.MockData
import com.example.skypulse.services.LocationService
import com.example.skypulse.services.WeatherService
import com.example.skypulse.types.WeatherApiResponse
import com.example.skypulse.ui.renders.RenderWeatherData

private sealed class HomeScreenState {
    object Loading : HomeScreenState()
    object PermissionDenied : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
    data class Success(
        val weatherData: WeatherApiResponse,
        val locationInfo: String
    ) : HomeScreenState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {}
) {
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
    val context = LocalContext.current
    val (permissionsGranted, requestPermission) = LocationService.rememberLocationPermission()
    var screenState by remember { mutableStateOf<HomeScreenState>(HomeScreenState.Loading) }

    // Request permits if they haven't been granted
    LaunchedEffect(Unit) {
        if (!permissionsGranted) requestPermission()
    }

    // Upload data when we've got permission
    LaunchedEffect(permissionsGranted) {
        if (!permissionsGranted) {
            screenState = HomeScreenState.PermissionDenied
            return@LaunchedEffect
        }

        screenState = HomeScreenState.Loading

        try {
            val (lat, lon) = LocationService.getUserLocation(context)

            if (lat == null || lon == null) {
                screenState = HomeScreenState.Error("Could not obtain location")
                Log.e("HomeScreen", "Error obtaining the user's geolocation")
                return@LaunchedEffect
            }

            val weatherData = WeatherService.getWeatherData(lat, lon)
            val locationInfo = LocationService.getLocationInfo(context, lat, lon)

            screenState = HomeScreenState.Success(
                weatherData = weatherData,
                locationInfo = "${locationInfo.city}, ${locationInfo.country}"
            )
        } catch (e: Exception) {
            screenState = HomeScreenState.Error(e.message ?: "Unknown error")
            Log.e("HomeScreen", "Error loading weather data", e)
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
            is HomeScreenState.Loading -> LoadingView(paddingValues)
            is HomeScreenState.PermissionDenied -> PermissionDeniedView(paddingValues)
            is HomeScreenState.Error -> ErrorView(state.message, paddingValues)
            is HomeScreenState.Success -> SuccessView(state, paddingValues)
        }
    }
}

@Composable
private fun LoadingView(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PermissionDeniedView(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CreateText("Location permission denied. Please enable it in settings.")
        // TODO: Add button to open settings or search manually
    }
}

@Composable
private fun ErrorView(
    message: String,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CreateText("Error: $message")
        // TODO: Add retry button
    }
}

@Composable
private fun SuccessView(
    state: HomeScreenState.Success,
    paddingValues: PaddingValues
) {
    val hourlyForecasts = MockData.getHourlyForecasts()
    val dailyForecasts = MockData.getDailyForecasts()

    RenderWeatherData(
        dailyForecasts,
        hourlyForecasts,
        state.weatherData,
        state.locationInfo,
        paddingValues
    )
}