package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.domain.models.HourlyForecast
import com.example.skypulse.enums.WeatherIconSize
import com.example.skypulse.utils.formatWeatherTime

@Composable
fun HourlyForecastRow(
    forecast: HourlyForecast,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = forecast.list) { forecastItem ->
            for (forecastItemWeather in forecastItem.weather) {
                Card(
                    modifier = modifier,
                    onClick = onClick,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CreateText(
                            text = forecastItem.dt.formatWeatherTime(LocalContext.current),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        WeatherIcon(
                            iconCode = forecastItemWeather.icon,
                            size = WeatherIconSize.SMALL
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        CreateText(
                            text = "${forecastItem.main.temp} °C",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}