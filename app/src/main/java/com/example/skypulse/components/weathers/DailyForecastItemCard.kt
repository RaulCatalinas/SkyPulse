package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skypulse.R
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.domain.models.DailyForecastItem
import com.example.skypulse.enums.WeatherIconSize
import com.example.skypulse.utils.formatWeatherDateTime

@Composable
fun DailyForecastItemCard(
    forecastItem: DailyForecastItem,
    modifier: Modifier = Modifier
) {
    val mainWeather = forecastItem.weather.firstOrNull() ?: return

    Card(
        modifier = modifier.padding(horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Fecha/Hora
            CreateText(
                text = forecastItem.dt.formatWeatherDateTime(LocalContext.current),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )

            // Icono del clima
            WeatherIcon(
                iconCode = mainWeather.icon,
                size = WeatherIconSize.MEDIUM,
            )

            // Descripción
            CreateText(
                text = mainWeather.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )

            // Temperatura
            CreateText(
                text = "${forecastItem.main.temp} °C",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            // Sensación térmica
            CreateText(
                text = "${stringResource(R.string.feels_like)}: ${forecastItem.main.feelsLike}°C",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}