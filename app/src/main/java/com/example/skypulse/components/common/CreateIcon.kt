package com.example.skypulse.components.common

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CreateIcon(
    icon: ImageVector,
    iconDescription: String,
    tint: Color = LocalContentColor.current,
    modifier: Modifier = Modifier
) {
    Icon(icon, contentDescription = iconDescription, tint = tint, modifier = modifier)
}