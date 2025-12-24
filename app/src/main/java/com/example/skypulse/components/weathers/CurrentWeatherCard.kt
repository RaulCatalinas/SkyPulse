package com.example.skypulse.components.weathers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skypulse.R
import com.example.skypulse.components.common.CreateIcon
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.enums.WeatherIconSize
import com.example.skypulse.types.WeatherApiResponse

/**
 * Main weather card showing current conditions
 */
@Composable
fun CurrentWeatherCard(
    weatherData: WeatherApiResponse,
    location: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4A90E2),
                            Color(0xFF67B8E3)
                        )
                    )
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Location
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CreateIcon(
                        Icons.Default.LocationOn,
                        iconDescription = "Location",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    CreateText(
                        text = location,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                WeatherIcon(
                    iconCode = weatherData.weather[0].icon,
                    size = WeatherIconSize.LARGE,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Temperature
                CreateText(
                    text = "${weatherData.main.temperature} °C",
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                CreateText(
                    text = weatherData.weather[0].description,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Feels like
                CreateText(
                    text = "${stringResource(R.string.feels_like)}: ${weatherData.main.feelsLike} °C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}