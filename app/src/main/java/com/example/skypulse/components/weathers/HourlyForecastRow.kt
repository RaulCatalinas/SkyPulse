package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.skypulse.models.HourlyForecast

/**
 * Horizontal scrolling hourly forecast
 */
@Composable
fun HourlyForecastRow(
    forecasts: List<HourlyForecast>,
    modifier: Modifier = Modifier,
    onItemClick: (HourlyForecast) -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) { forecast ->
            HourlyForecastCard(
                forecast = forecast,
                onClick = { onItemClick(forecast) }
            )
        }
    }
}