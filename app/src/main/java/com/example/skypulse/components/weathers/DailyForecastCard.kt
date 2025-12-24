package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skypulse.R
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.enums.WeatherIconSize
import com.example.skypulse.types.ForecastApiResponse
import com.example.skypulse.utils.formatWeatherDateTime

@Composable
fun DailyForecastCard(
    forecast: ForecastApiResponse,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(forecast.list) { forecastItem ->
                for (forecastItemWeather in forecastItem.weather) {
                    Column(
                        modifier = Modifier.padding(horizontal = 8.dp),
                    ) {

                        CreateText(
                            text = forecastItem.dt.formatWeatherDateTime(context),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        WeatherIcon(
                            iconCode = forecastItemWeather.icon,
                            size = WeatherIconSize.MEDIUM,
                        )

                        CreateText(
                            text = forecastItemWeather.description,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        CreateText(
                            text = "${forecastItem.main.tempMax} °C / ${forecastItem.main.tempMin} °C",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        CreateText(
                            text = "${stringResource(R.string.feels_like)}: ${forecastItem.main.feelsLike} °C",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}