package com.example.skypulse.enums

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WeatherIconSize(
    val suffix: String,
    val sizeDp: Dp
) {
    SMALL("@2x", 56.dp),
    MEDIUM("@2x", 88.dp),
    LARGE("@4x", 128.dp)
}