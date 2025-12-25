package com.example.skypulse.domain.mappers

import com.example.skypulse.domain.models.HourlyForecast
import com.example.skypulse.domain.models.HourlyForecastCity
import com.example.skypulse.domain.models.HourlyForecastClouds
import com.example.skypulse.domain.models.HourlyForecastCoord
import com.example.skypulse.domain.models.HourlyForecastItem
import com.example.skypulse.domain.models.HourlyForecastMain
import com.example.skypulse.domain.models.HourlyForecastRain
import com.example.skypulse.domain.models.HourlyForecastSys
import com.example.skypulse.domain.models.HourlyForecastWeather
import com.example.skypulse.domain.models.HourlyForecastWind
import com.example.skypulse.types.HourlyForecastApiResponse
import com.example.skypulse.utils.capitalizeFirstLetter
import kotlin.math.roundToInt

fun HourlyForecastApiResponse.toUiModel(): HourlyForecast {
    return HourlyForecast(
        cnt = cnt,
        cod = cod,
        message = message,
        city = HourlyForecastCity(
            id = city.id,
            name = city.name,
            country = city.country,
            timezone = city.timezone,
            sunrise = city.sunrise,
            sunset = city.sunset,
            population = city.population,
            coord = HourlyForecastCoord(
                lat = city.coord.lat,
                lon = city.coord.lon
            )
        ),
        list = list.map {
            HourlyForecastItem(
                clouds = HourlyForecastClouds(
                    all = it.clouds.all
                ),
                dt = it.dt,
                dtTxt = it.dtTxt,
                main = HourlyForecastMain(
                    temp = it.main.temp.roundToInt(),
                    feelsLike = it.main.feelsLike.roundToInt(),
                    tempMin = it.main.tempMin.roundToInt(),
                    tempMax = it.main.tempMax.roundToInt(),
                    pressure = it.main.pressure,
                    seaLevel = it.main.seaLevel,
                    grndLevel = it.main.grndLevel,
                    humidity = it.main.humidity,
                    tempKf = it.main.tempKf.roundToInt()
                ),
                pop = it.pop,
                rain = HourlyForecastRain(
                    h = it.rain?.h ?: 0.0
                ),
                sys = HourlyForecastSys(
                    pod = it.sys.pod
                ),
                visibility = it.visibility,
                weather = it.weather.map { weatherItem ->
                    HourlyForecastWeather(
                        id = weatherItem.id,
                        main = weatherItem.main,
                        description = weatherItem.description.capitalizeFirstLetter(),
                        icon = weatherItem.icon
                    )
                },
                wind = HourlyForecastWind(
                    speed = it.wind.speed,
                    deg = it.wind.deg,
                    gust = it.wind.gust
                ),
            )
        }
    )
}

