package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.skypulse.R
import com.example.skypulse.domain.models.CurrentWeather

/**
 * Grid of weather details (humidity, wind, etc.)
 */
@Composable
fun WeatherDetailsGrid(
    weatherData: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailItem(
            icon = "💧",
            label = stringResource(R.string.humidity),
            value = "${weatherData.main.humidity}%"
        )
        WeatherDetailItem(
            icon = "💨",
            label = stringResource(R.string.wind_speed),
            value = "${weatherData.wind.speed} km/h"
        )
    }
}