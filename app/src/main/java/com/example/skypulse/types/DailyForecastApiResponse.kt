package com.example.skypulse.types

import com.google.gson.annotations.SerializedName

data class ForecastApiResponse(
    val city: ForecastCity,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItem>,
    val message: Int
)

data class ForecastCity(
    val coord: ForecastCoord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class ForecastItem(
    val clouds: ForecastClouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: ForecastMain,
    val pop: Double,
    val rain: ForecastRain?,
    val sys: ForecastSys,
    val visibility: Int,
    val weather: List<ForecastWeather>,
    val wind: ForecastWind
)

data class ForecastCoord(
    val lat: Double,
    val lon: Double
)

data class ForecastClouds(
    val all: Int
)

data class ForecastMain(
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

data class ForecastRain(
    @SerializedName("3h")
    val h: Double
)

data class ForecastSys(
    val pod: String
)

data class ForecastWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class ForecastWind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)