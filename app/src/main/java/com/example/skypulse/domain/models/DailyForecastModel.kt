package com.example.skypulse.domain.models

data class DailyForecastWeather(
    val city: DailyForecastCity,
    val cnt: Int,
    val cod: String,
    val list: List<DailyForecastItem>,
    val message: Int
)

data class DailyForecastCity(
    val coord: DailyForecastCoordinates,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class DailyForecastItem(
    val clouds: DailyForecastItemClouds,
    val dt: Int,
    val dtTxt: String,
    val main: MainDailyForecast,
    val pop: Double,
    val rain: DailyForecastItemRain?,
    val sys: DailyForecastItemSys,
    val visibility: Int,
    val weather: List<DailyForecastItemWeather>,
    val wind: DailyForecastItemWind
)

data class DailyForecastCoordinates(
    val lat: Double,
    val lon: Double
)

data class DailyForecastItemClouds(
    val all: Int
)

data class MainDailyForecast(
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

data class DailyForecastItemRain(
    val h: Double
)

data class DailyForecastItemSys(
    val pod: String
)

data class DailyForecastItemWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class DailyForecastItemWind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)
