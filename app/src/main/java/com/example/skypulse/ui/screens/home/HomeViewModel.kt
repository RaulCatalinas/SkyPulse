package com.example.skypulse.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skypulse.repositories.WeatherRepository
import com.example.skypulse.services.LocationService
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

    fun loadHomeData(context: Context) {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading

            // Get user location
            val locationResult = LocationService.getUserLocation(context)
            if (locationResult.isFailure) {
                _state.value = HomeScreenState.Error(WeatherUiError.Unknown)
                return@launch
            }

            val location = locationResult.getOrNull()!!

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
                        LocationService.getLocationInfo(
                            context,
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
                        val locationInfo = locationInfoResult.getOrThrow()

                        _state.value = HomeScreenState.Success(
                            weatherData = weatherData,
                            forecastData = forecastData,
                            locationInfo = "${locationInfo.city}, ${locationInfo.country}"
                        )
                    }
                }
            }
        }
    }
}