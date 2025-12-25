package com.example.skypulse.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.components.sections.SectionHeader
import com.example.skypulse.components.weathers.CurrentWeatherCard
import com.example.skypulse.components.weathers.DailyForecastRow
import com.example.skypulse.components.weathers.HourlyForecastRow
import com.example.skypulse.components.weathers.WeatherDetailsGrid
import com.example.skypulse.ui.screens.states.HomeScreenState

@Composable
fun HomeSuccessView(
    state: HomeScreenState.Success,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            CurrentWeatherCard(
                state.weatherData,
                state.locationInfo,
            )
        }

        item {
            WeatherDetailsGrid(state.weatherData)
        }

        item {
            SectionHeader("Hourly Forecast")
            HourlyForecastRow(state.hourlyForecastData)
        }

        item {
            SectionHeader("7-Day Forecast")
            DailyForecastRow(state.forecastData)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}