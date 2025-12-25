package com.example.skypulse.types

import com.google.gson.annotations.SerializedName

data class HourlyForecastApiResponse(
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
    @SerializedName("dt_txt")
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
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    val pressure: Int,
    @SerializedName("sea_level")
    val seaLevel: Int,
    val temp: Double,
    @SerializedName("temp_kf")
    val tempKf: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    @SerializedName("temp_min")
    val tempMin: Double
)

data class HourlyForecastRain(
    @SerializedName("1h")
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


