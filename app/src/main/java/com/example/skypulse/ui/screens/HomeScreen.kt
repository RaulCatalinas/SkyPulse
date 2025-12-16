package com.example.skypulse.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.skypulse.ui.screens.home.HomeContentView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {}
) {
    SettingsScreen.WithDrawer { onSettingsClick ->
        HomeContentView(
            onSearchClick = onSearchClick,
            onFavoritesClick = onFavoritesClick,
            onSettingsClick = onSettingsClick
        )
    }
}
