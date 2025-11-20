package com.example.skypulse.components.common

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CreateIconButton(icon: ImageVector, iconDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        CreateIcon(icon = icon, iconDescription = iconDescription)
    }
}