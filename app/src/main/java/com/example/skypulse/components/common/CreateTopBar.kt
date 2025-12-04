package com.example.skypulse.components.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTopBar(
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        title = {
            CreateText(
                text = "SkyPulse",
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            CreateIconButton(
                icon = Icons.Default.Search,
                iconDescription = "Search",
                onClick = onSearchClick
            )
            CreateIconButton(
                icon = Icons.Default.Star,
                iconDescription = "Favorites",
                onClick = onFavoritesClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            CreateIconButton(
                icon = Icons.Default.Settings,
                iconDescription = "Settings",
                onClick = onSettingsClick
            )
        }
    )
}