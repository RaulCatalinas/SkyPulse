package com.example.skypulse.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.example.skypulse.services.LocationInfo
import com.example.skypulse.services.LocationResult
import com.example.skypulse.services.LocationService
import com.example.skypulse.services.UserLocationState

object LocationRepository {
    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context
    }

    @Composable
    fun rememberLocationPermission(): Pair<Boolean, () -> Unit> {
        var permissionGranted by remember { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            permissionGranted = isGranted
        }

        LaunchedEffect(Unit) {
            permissionGranted = ContextCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        val requestPermission = {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        return Pair(permissionGranted, requestPermission)
    }

    suspend fun getUserLocation(): LocationResult {
        @SuppressWarnings("CanBeLocal")
        val userLocation =
            LocationService.getUserLocation(appContext)

        when (userLocation) {
            is UserLocationState.Error -> error("Failed to fetch user location: ${userLocation.message}")
            is UserLocationState.Success<LocationResult> -> return userLocation.data
        }
    }

    suspend fun getLocationInfo(lat: Double, lon: Double): LocationInfo {
        @SuppressWarnings("CanBeLocal")
        val locationInfo =
            LocationService.getUserLocationInfo(
                appContext,
                lat,
                lon
            )

        return when (locationInfo) {
            is UserLocationState.Error -> {
                Log.e(
                    "LocationRepository",
                    "Failed to fetch location info: ${locationInfo.message}"
                )

                LocationInfo.UNKNOWN
            }

            is UserLocationState.Success<LocationInfo> -> return locationInfo.data
        }
    }
}