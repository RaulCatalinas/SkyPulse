package com.example.skypulse.components.weathers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skypulse.components.common.CreateText
import com.example.skypulse.models.HourlyForecast
import com.example.skypulse.utils.toWeatherIcon

@Composable
fun HourlyForecastCard(
    forecast: HourlyForecast,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
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
                text = forecast.time,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))

            // CAMBIADO: Usa Icon en lugar de Text con emoji
            Icon(
                imageVector = forecast.icon.toWeatherIcon(),
                contentDescription = forecast.description,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            CreateText(
                text = "${forecast.temperature}°",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}