package com.example.skypulse.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
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
import kotlin.coroutines.Continuation
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
        var locationPermissionGranted by remember { mutableStateOf(false) }
        var internetPermissionGranted by remember { mutableStateOf(false) }

        // Launcher for multiple permissions
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            locationPermissionGranted =
                permissionsMap[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
            internetPermissionGranted =
                permissionsMap[Manifest.permission.INTERNET] ?: false
        }

        LaunchedEffect(Unit) {
            locationPermissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            internetPermissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.INTERNET
            ) == PackageManager.PERMISSION_GRANTED
        }

        val requestPermission = {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.INTERNET
                )
            )
        }

        // Fine location is optional - we only require coarse location and internet
        val allPermissionsGranted =
            locationPermissionGranted && internetPermissionGranted

        return Pair(allPermissionsGranted, requestPermission)
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
     * @return LocationInfo with city and country
     */
    suspend fun getLocationInfo(
        context: Context,
        latitude: Double,
        longitude: Double
    ): LocationInfo {
        return suspendCoroutine { continuation ->
            try {
                val geocoder = Geocoder(context)
                var addresses: List<Address>?

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, 1) { addrs ->
                        addresses = addrs
                        // Now resume the outer continuation
                        handleAddresses(addresses, continuation)
                    }
                } else {
                    @Suppress("DEPRECATION")
                    addresses = geocoder.getFromLocation(latitude, longitude, 1)

                    handleAddresses(addresses, continuation)
                }
            } catch (e: Exception) {
                Log.e("LocationService", "Error: ${e.message}", e)
                continuation.resume(
                    LocationInfo(
                        city = "Unknown",
                        country = "Unknown"
                    )
                )
            }
        }
    }

    private fun handleAddresses(
        addresses: List<Address>?,
        continuation: Continuation<LocationInfo>
    ) {
        if (addresses.isNullOrEmpty()) {
            continuation.resume(
                LocationInfo(
                    city = "Unknown",
                    country = "Unknown"
                )
            )
        } else {
            val address = addresses[0]
            val city = address.locality ?: address.adminArea
            val country = address.countryName

            Log.d("LocationService", "Geocoded: City=$city, Country=$country")

            continuation.resume(
                LocationInfo(
                    city = city,
                    country = country
                )
            )
        }
    }
}