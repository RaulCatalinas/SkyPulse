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
import com.example.skypulse.types.LocationInfo
import com.example.skypulse.types.LocationResult
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationService {
    private const val TAG = "LocationService"

    @Composable
    fun rememberLocationPermission(): Pair<Boolean, () -> Unit> {
        val context = LocalContext.current
        var permissionGranted by remember { mutableStateOf(false) }

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            permissionGranted = isGranted
        }

        LaunchedEffect(Unit) {
            permissionGranted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        }

        val requestPermission = {
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        return Pair(permissionGranted, requestPermission)
    }

    /**
     * Get current location
     * @return Result with LocationResult or error
     */
    suspend fun getUserLocation(context: Context): Result<LocationResult> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        return suspendCoroutine { continuation ->
            try {
                fusedLocationClient
                    .lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location != null) {
                            continuation.resume(
                                Result.success(
                                    LocationResult(
                                        latitude = location.latitude,
                                        longitude = location.longitude
                                    )
                                )
                            )
                        } else {
                            Log.e(TAG, "Location is null")
                            continuation.resume(
                                Result.failure(Exception("Location not available"))
                            )
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to get location", exception)
                        continuation.resume(Result.failure(exception))
                    }
            } catch (e: SecurityException) {
                Log.e(TAG, "Security exception", e)
                continuation.resume(Result.failure(e))
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        continuation.resume(parseAddress(addresses))
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                    continuation.resume(parseAddress(addresses))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Geocoding error", e)
                continuation.resume(LocationInfo.UNKNOWN)
            }
        }
    }

    private fun parseAddress(addresses: List<Address>?): LocationInfo {
        val address = addresses?.firstOrNull() ?: return LocationInfo.UNKNOWN

        val city = address.locality ?: address.adminArea ?: "Unknown"
        val country = address.countryName ?: "Unknown"

        Log.d(TAG, "Geocoded: City=$city, Country=$country")

        return LocationInfo(city = city, country = country)
    }
}