package com.example.skypulse.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
    private lateinit var _context: Context

    fun initialize(context: Context) {
        _context = context
    }

    @Composable
    fun RememberLocationPermission(): Pair<Boolean, () -> Unit> {
        var permissionGranted by remember { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            permissionGranted = isGranted
        }

        LaunchedEffect(Unit) {
            permissionGranted = ContextCompat.checkSelfPermission(
                _context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        val requestPermission = {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        return Pair(permissionGranted, requestPermission)
    }

    suspend fun getUserLocation(): UserLocationState<LocationResult> {
        return LocationService.getUserLocation(_context)
    }

    suspend fun getLocationInfo(lat: Double, lon: Double): UserLocationState<LocationInfo> {
        return LocationService.getUserLocationInfo(_context, lat, lon)
    }
}