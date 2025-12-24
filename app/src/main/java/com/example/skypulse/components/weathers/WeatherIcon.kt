package com.example.skypulse.components.weathers

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.skypulse.enums.WeatherIconSize

@Composable
fun WeatherIcon(
    iconCode: String,
    size: WeatherIconSize
) {
    SubcomposeAsyncImage(
        model =
            ImageRequest
                .Builder(LocalContext.current)
                .apply {
                    data("https://openweathermap.org/img/wn/${iconCode}${size.suffix}.png")
                    crossfade(300)
                }
                .build(),
        contentDescription = "Weather icon",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(size.sizeDp),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.size(size.sizeDp / 3),
                strokeWidth = 2.dp,
                color = Color.Gray
            )
        },
        onError = {
            Log.e(
                "WeatherIcon",
                "Error loading weather icon: ${it.result}",
                it.result.throwable
            )
        },
    )
}
