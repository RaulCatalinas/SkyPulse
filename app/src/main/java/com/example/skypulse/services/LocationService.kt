package com.example.skypulse.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
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

data class LocationInfo(
    val city: String?,
    val country: String?
)

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

    /**
     * Get location information including city and country from coordinates
     * Uses reverse geocoding
     *
     * @param context Application context
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @return LocationInfo with latitude, longitude, city and country
     */
    suspend fun getLocationInfo(
        context: Context,
        latitude: Double,
        longitude: Double
    ): LocationInfo {
        return suspendCoroutine { continuation ->
            try {
                val geocoder = Geocoder(context)

                // getFromLocation requiere API 33+, usar version compatible
                val addresses: List<Address>? =
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                        var result: List<Address>? = null
                        geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                            result = addresses
                        }
                        result
                    } else {
                        @Suppress("DEPRECATION")
                        geocoder.getFromLocation(latitude, longitude, 1)
                    }

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val city = address.locality ?: address.adminArea ?: "Unknown"
                    val country = address.countryName ?: "Unknown"

                    Log.d(
                        "LocationService",
                        "Geocoded location: City=$city, Country=$country"
                    )

                    continuation.resume(
                        LocationInfo(
                            city = city,
                            country = country
                        )
                    )
                } else {
                    Log.e("LocationService", "No address found for coordinates")
                    continuation.resume(
                        LocationInfo(
                            city = null,
                            country = null
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "LocationService",
                    "Error during geocoding: ${e.message}",
                    e
                )
                continuation.resume(
                    LocationInfo(
                        city = null,
                        country = null
                    )
                )
            }
        }
    }
}