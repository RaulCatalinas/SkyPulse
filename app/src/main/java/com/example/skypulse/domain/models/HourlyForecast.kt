package com.example.skypulse.domain.models

data class HourlyForecast(
    val city: HourlyForecastCity,
    val cnt: Int,
    val cod: String,
    val list: List<HourlyForecastItem>,
    val message: Int
)

data class HourlyForecastCity(
    val coord: HourlyForecastCoord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class HourlyForecastItem(
    val clouds: HourlyForecastClouds,
    val dt: Int,
    val dtTxt: String,
    val main: HourlyForecastMain,
    val pop: Double,
    val rain: HourlyForecastRain?,
    val sys: HourlyForecastSys,
    val visibility: Int,
    val weather: List<HourlyForecastWeather>,
    val wind: HourlyForecastWind
)

data class HourlyForecastCoord(
    val lat: Double,
    val lon: Double
)

data class HourlyForecastClouds(
    val all: Int
)

data class HourlyForecastMain(
    val feelsLike: Int,
    val grndLevel: Int,
    val humidity: Int,
    val pressure: Int,
    val seaLevel: Int,
    val temp: Int,
    val tempKf: Int,
    val tempMax: Int,
    val tempMin: Int
)

data class HourlyForecastRain(
    val h: Double
)

data class HourlyForecastSys(
    val pod: String
)

data class HourlyForecastWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class HourlyForecastWind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)


