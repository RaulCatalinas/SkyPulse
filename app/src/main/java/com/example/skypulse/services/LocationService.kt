package com.example.skypulse.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationService {
    @Composable
    fun rememberLocationPermission(): Pair<Boolean, () -> Unit> {
        val context = LocalContext.current
        var permissionsGranted by remember { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            permissionsGranted = isGranted
        }

        LaunchedEffect(Unit) {
            permissionsGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        val requestPermission = {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        return Pair(permissionsGranted, requestPermission)
    }

    /**
     * Get current location
     * Throws SecurityException if permission not granted
     * Throws IllegalStateException if service not initialized
     *
     * @return LocationResult with latitude and longitude
     */
    suspend fun getUserLocation(context: Context): Pair<Double?, Double?> {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        return suspendCoroutine { continuation ->
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        continuation.resume(
                            Pair(
                                location?.latitude,
                                location?.longitude
                            )
                        )
                    }
                    .addOnFailureListener { exception ->
                        Log.e(
                            "LocationService",
                            exception.message ?: "Unknown error",
                            exception
                        )

                        continuation.resume(
                            Pair(
                                null,
                                null
                            )
                        )
                    }
            } catch (e: SecurityException) {
                Log.e(
                    "LocationService",
                    "Security exception: ${e.message}",
                    e
                )

                continuation.resume(
                    Pair(
                        null,
                        null
                    )
                )
            }
        }
    }
}