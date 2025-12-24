package com.example.skypulse.components.weathers

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.skypulse.enums.WeatherIconSize

@Composable
fun WeatherIcon(
    iconCode: String,
    size: WeatherIconSize
) {
    val sizeResolver = rememberConstraintsSizeResolver()
    Box(
        modifier =
            Modifier
                .fillMaxWidth(0.3f)
                .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .apply {
                        data("https://openweathermap.org/img/wn/${iconCode}${size.suffix}.png")
                        crossfade(true)
                        size(sizeResolver)
                    }
                    .build(),
            contentDescription = "Weather icon",
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
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
}
