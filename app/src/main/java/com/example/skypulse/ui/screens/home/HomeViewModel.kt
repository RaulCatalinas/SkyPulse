package com.example.skypulse.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skypulse.services.LocationService
import com.example.skypulse.services.WeatherService
import com.example.skypulse.types.ApiRequest
import com.example.skypulse.ui.mappers.WeatherUiError
import com.example.skypulse.ui.mappers.toUiError
import com.example.skypulse.ui.screens.states.HomeScreenState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class HomeViewModel : ViewModel() {
    private val _state =
        MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)

    val state: StateFlow<HomeScreenState> = _state

    fun loadHomeData(context: Context) {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            val locationResult = LocationService.getUserLocation(context)

            if (locationResult.isFailure) {
                _state.value = HomeScreenState.Error(WeatherUiError.Unknown)
                return@launch
            }

            val location = locationResult.getOrNull()!!

            supervisorScope {
                val weatherDeferred = async {
                    runCatching {
                        WeatherService
                            .getWeatherInfo(
                                ApiRequest.GET_WEATHER,
                                location.latitude,
                                location.longitude
                            )
                            .getOrThrow()
                    }
                }

                val forecastDeferred = async {
                    runCatching {
                        WeatherService
                            .getWeatherInfo(
                                ApiRequest.GET_FORECAST,
                                location.latitude,
                                location.longitude
                            )
                            .getOrThrow()
                    }
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

                val weatherResult = weatherDeferred.await()
                val forecastResult = forecastDeferred.await()
                val locationInfoResult = locationInfoDeferred.await()

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
                        val weather =
                            weatherResult
                                .getOrNull()
                                    as WeatherService.WeatherResult.Weather

                        val forecast =
                            forecastResult
                                .getOrNull()
                                    as WeatherService.WeatherResult.Forecast

                        val locationInfo = locationInfoResult.getOrThrow()

                        _state.value = HomeScreenState.Success(
                            weatherData = weather.data,
                            forecastData = forecast.data,
                            locationInfo =
                                "${locationInfo.city}, ${locationInfo.country}"
                        )
                    }
                }
            }
        }
    }
}
