package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.types.WeatherApiResponse

/**
 * Grid of weather details (humidity, wind, etc.)
 */
@Composable
fun WeatherDetailsGrid(
    weatherData: WeatherApiResponse,
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
            label = "Humidity",
            value = "${weatherData.main.humidity}%"
        )
        WeatherDetailItem(
            icon = "💨",
            label = "Wind",
            value = "${weatherData.wind.speed} km/h"
        )
    }
}