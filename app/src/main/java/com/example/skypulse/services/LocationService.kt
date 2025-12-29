package com.example.skypulse.services

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationService {
    private const val TAG = "LocationService"

    /**
     * Get current location
     * @return Result with LocationResult or error
     */
    suspend fun getUserLocation(context: Context): UserLocationState<LocationResult> {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        return suspendCoroutine {
            try {
                fusedLocationClient
                    .lastLocation
                    .addOnSuccessListener { location: Location? ->
                        if (location == null) {
                            Log.e(TAG, "Location is null")

                            it.resume(
                                UserLocationState.Error("Location not available")
                            )

                            return@addOnSuccessListener
                        }

                        it.resume(
                            UserLocationState.Success(
                                LocationResult(
                                    latitude = location.latitude,
                                    longitude = location.longitude
                                )
                            )
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Failed to get location", e)

                        it.resume(
                            UserLocationState.Error("Failed to get location: ${e.message}")
                        )
                    }
            } catch (e: SecurityException) {
                Log.e(TAG, "Security exception", e)
                it.resume(
                    UserLocationState.Error("Security exception: ${e.message}")
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
    suspend fun getUserLocationInfo(
        context: Context,
        latitude: Double,
        longitude: Double
    ): UserLocationState<LocationInfo> {
        return suspendCoroutine {
            try {
                val geocoder = Geocoder(context)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        it.resume(
                            UserLocationState.Success(parseAddress(addresses))
                        )
                    }

                    return@suspendCoroutine
                }

                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                it.resume(
                    UserLocationState.Success(parseAddress(addresses))
                )
            } catch (e: Exception) {
                Log.e(TAG, "Geocoding error", e)

                it.resume(
                    UserLocationState.Error("Unknown error")
                )
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

sealed class UserLocationState<out T> {
    data class Success<T>(val data: T) : UserLocationState<T>()
    data class Error(val message: String) : UserLocationState<Nothing>()
}

data class LocationResult(
    val latitude: Double,
    val longitude: Double
)

data class LocationInfo(
    val city: String,
    val country: String
) {
    companion object {
        val UNKNOWN = LocationInfo("Unknown", "Unknown")
    }
}