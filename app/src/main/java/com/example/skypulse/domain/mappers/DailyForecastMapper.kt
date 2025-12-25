package com.example.skypulse.domain.mappers

import com.example.skypulse.domain.models.DailyForecastCity
import com.example.skypulse.domain.models.DailyForecastCoordinates
import com.example.skypulse.domain.models.DailyForecastItem
import com.example.skypulse.domain.models.DailyForecastItemClouds
import com.example.skypulse.domain.models.DailyForecastItemRain
import com.example.skypulse.domain.models.DailyForecastItemSys
import com.example.skypulse.domain.models.DailyForecastItemWeather
import com.example.skypulse.domain.models.DailyForecastItemWind
import com.example.skypulse.domain.models.DailyForecastWeather
import com.example.skypulse.domain.models.MainDailyForecast
import com.example.skypulse.types.ForecastApiResponse
import com.example.skypulse.utils.capitalizeFirstLetter

fun ForecastApiResponse.toUiModel(): DailyForecastWeather {
    return DailyForecastWeather(
        city = DailyForecastCity(
            coord = DailyForecastCoordinates(
                lat = city.coord.lat,
                lon = city.coord.lon
            ),
            country = city.country,
            id = city.id,
            name = city.name,
            population = city.population,
            sunrise = city.sunrise,
            sunset = city.sunset,
            timezone = city.timezone
        ),
        cnt = cnt,
        cod = cod,
        message = message,
        list = list.map {
            DailyForecastItem(
                clouds = DailyForecastItemClouds(all = it.clouds.all),
                dt = it.dt,
                dtTxt = it.dtTxt,
                main = MainDailyForecast(
                    feelsLike = it.main.feelsLike.toInt(),
                    grndLevel = it.main.grndLevel,
                    humidity = it.main.humidity,
                    pressure = it.main.pressure,
                    seaLevel = it.main.seaLevel,
                    temp = it.main.temp.toInt(),
                    tempKf = it.main.tempKf.toInt(),
                    tempMax = it.main.tempMax.toInt(),
                    tempMin = it.main.tempMin.toInt()
                ),
                pop = it.pop,
                rain = DailyForecastItemRain(h = it.rain?.h ?: 0.0),
                sys = DailyForecastItemSys(pod = it.sys.pod),
                visibility = it.visibility,
                weather = it.weather.map { weather ->
                    DailyForecastItemWeather(
                        description = weather.description.capitalizeFirstLetter(),
                        icon = weather.icon,
                        id = weather.id,
                        main = weather.main
                    )
                },
                wind = DailyForecastItemWind(
                    deg = it.wind.deg,
                    gust = it.wind.gust,
                    speed = it.wind.speed
                )
            )
        }
    )
}