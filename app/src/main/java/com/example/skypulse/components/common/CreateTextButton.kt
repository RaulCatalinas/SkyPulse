package com.example.skypulse.components.common

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CreateTextButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        CreateText(text = text)
    }
}