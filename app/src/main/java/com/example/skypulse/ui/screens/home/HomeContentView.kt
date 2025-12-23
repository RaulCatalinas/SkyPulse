package com.example.skypulse.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.skypulse.R
import com.example.skypulse.components.common.CreateTopBar
import com.example.skypulse.services.LocationService
import com.example.skypulse.ui.mappers.WeatherUiError
import com.example.skypulse.ui.screens.states.HomeScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContentView(
    viewModel: HomeViewModel = viewModel(),
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    val (
        permissionsGranted,
        _
    ) = LocationService.rememberLocationPermission()

    LaunchedEffect(Unit) {
        if (!permissionsGranted) requestPermission()
    }

    LaunchedEffect(permissionsGranted) {
        if (permissionsGranted) {
            viewModel.loadHomeData(context)
        }
    }

    Scaffold(
        topBar = {
            CreateTopBar(
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                onSettingsClick = onSettingsClick
            )
        }
    ) { paddingValues ->
        when (val screenState = state) {
            HomeScreenState.Loading -> HomeLoadingView(paddingValues)
            HomeScreenState.PermissionDenied -> HomePermissionDeniedView(paddingValues)
            is HomeScreenState.Success -> HomeSuccessView(screenState, paddingValues)
            is HomeScreenState.Error -> {
                val message = weatherErrorMessage(screenState.error)
                HomeErrorView(message, paddingValues)
            }
        }
    }
}

@Composable
fun weatherErrorMessage(error: WeatherUiError): String {
    return when (error) {
        WeatherUiError.Timeout -> stringResource(R.string.error_timeout)
        WeatherUiError.Network -> stringResource(R.string.error_network)
        WeatherUiError.Unknown -> stringResource(R.string.error_unknown)
    }
}
