package com.example.skypulse.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skypulse.repositories.LocationRepository
import com.example.skypulse.repositories.WeatherRepository
import com.example.skypulse.services.LocationInfo
import com.example.skypulse.services.LocationResult
import com.example.skypulse.services.UserLocationState
import com.example.skypulse.ui.mappers.WeatherUiError
import com.example.skypulse.ui.mappers.toUiError
import com.example.skypulse.ui.screens.states.HomeScreenState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state

    fun loadHomeData() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading

            // Get user location
            when (val locationResult = LocationRepository.getUserLocation()) {
                is UserLocationState.Error -> {
                    _state.value = HomeScreenState.Error(WeatherUiError.Unknown)
                }

                is UserLocationState.Success<LocationResult> -> loadAllData(locationResult.data)
            }
        }
    }

    private suspend fun loadAllData(location: LocationResult) {
        // Fetch all data in parallel
        supervisorScope {
            val weatherDeferred = async {
                WeatherRepository.getCurrentWeather(
                    location.latitude,
                    location.longitude
                )
            }

            val forecastDeferred = async {
                WeatherRepository.getForecast(
                    location.latitude,
                    location.longitude
                )
            }

            val locationInfoDeferred = async {
                runCatching {
                    LocationRepository.getLocationInfo(
                        location.latitude,
                        location.longitude
                    )
                }
            }

            // Await all results
            val weatherResult = weatherDeferred.await()
            val forecastResult = forecastDeferred.await()
            val locationInfoResult = locationInfoDeferred.await()

            // Handle results
            when {
                weatherResult.isFailure -> {
                    _state.value = HomeScreenState.Error(
                        weatherResult.toUiError()
                    )
                }

                forecastResult.isFailure -> {
                    _state.value = HomeScreenState.Error(
                        forecastResult.toUiError()
                    )
                }

                locationInfoResult.isFailure -> {
                    _state.value = HomeScreenState.Error(WeatherUiError.Unknown)
                }

                else -> {
                    val weatherData = weatherResult.getOrThrow()
                    val forecastData = forecastResult.getOrThrow()
                    val locationInfo = locationInfoResult.getOrNull()

                    if (locationInfo == null || locationInfo is UserLocationState.Error) {
                        Log.e(
                            "HomeViewModel",
                            "Failed to get location info: ${locationInfo?.message}"
                        )

                        _state.value = HomeScreenState.Success(
                            weatherData = weatherData,
                            forecastData = forecastData,
                            locationInfo = "Unknow, Unknow"
                        )

                        return@supervisorScope
                    }

                    val (
                        city,
                        country
                    ) = (locationInfo as UserLocationState.Success<LocationInfo>).data

                    _state.value = HomeScreenState.Success(
                        weatherData = weatherData,
                        forecastData = forecastData,
                        locationInfo = "$city, $country"
                    )
                }
            }
        }
    }

}