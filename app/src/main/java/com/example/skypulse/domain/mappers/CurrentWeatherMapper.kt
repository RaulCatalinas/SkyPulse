package com.example.skypulse.domain.mappers

import com.example.skypulse.domain.models.Clouds
import com.example.skypulse.domain.models.Coordinates
import com.example.skypulse.domain.models.CurrentWeather
import com.example.skypulse.domain.models.MainCurrentWeather
import com.example.skypulse.domain.models.Sys
import com.example.skypulse.domain.models.Weather
import com.example.skypulse.domain.models.Wind
import com.example.skypulse.types.WeatherApiResponse
import com.example.skypulse.utils.capitalizeFirstLetter

fun WeatherApiResponse.toUiModel(): CurrentWeather {
    return CurrentWeather(
        coordinates = Coordinates(
            longitude = coordinates.longitude,
            latitude = coordinates.latitude
        ),
        weather = weather.map {
            Weather(
                id = it.id,
                main = it.main,
                description = it.description.capitalizeFirstLetter(),
                icon = it.icon
            )
        },
        base = base,
        main = MainCurrentWeather(
            temperature = main.temperature.toInt(),
            feelsLike = main.feelsLike.toInt(),
            tempMin = main.tempMin.toInt(),
            tempMax = main.tempMax.toInt(),
            pressure = main.pressure,
            humidity = main.humidity,
            seaLevel = main.seaLevel,
            groundLevel = main.groundLevel
        ),
        visibility = visibility,
        wind = Wind(
            speed = wind.speed,
            degrees = wind.degrees,
            gust = wind.gust
        ),
        clouds = Clouds(cloudiness = clouds.cloudiness),
        timestamp = timestamp,
        sys = Sys(
            type = sys.type,
            id = sys.id,
            country = sys.country,
            sunrise = sys.sunrise,
            sunset = sys.sunset
        ),
        timezone = timezone,
        cityId = cityId,
        cityName = cityName,
        statusCode = statusCode
    )
}
