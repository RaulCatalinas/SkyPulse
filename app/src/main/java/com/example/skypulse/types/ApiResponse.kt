package com.example.skypulse.types

import com.google.gson.annotations.SerializedName

/**
 * Current Weather API Response
 * Endpoint: /weather
 */
data class WeatherApiResponse(
    @SerializedName("coord")
    val coordinates: CoordinatesDto,

    @SerializedName("weather")
    val weather: List<WeatherDto>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val main: MainDto,

    @SerializedName("visibility")
    val visibility: Int,

    @SerializedName("wind")
    val wind: WindDto,

    @SerializedName("clouds")
    val clouds: CloudsDto,

    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("sys")
    val sys: SysDto,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("id")
    val cityId: Int,

    @SerializedName("name")
    val cityName: String,

    @SerializedName("cod")
    val statusCode: Int
)

data class CoordinatesDto(
    @SerializedName("lon")
    val longitude: Double,

    @SerializedName("lat")
    val latitude: Double
)

data class WeatherDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
)

data class MainDto(
    @SerializedName("temp")
    val temperature: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("sea_level")
    val seaLevel: Int? = null,

    @SerializedName("grnd_level")
    val groundLevel: Int? = null
)

data class WindDto(
    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val degrees: Int,

    @SerializedName("gust")
    val gust: Double? = null
)

data class CloudsDto(
    @SerializedName("all")
    val cloudiness: Int
)

data class SysDto(
    @SerializedName("type")
    val type: Int? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)

// ==================== 5 Day Forecast API Response ====================

/**
 * 5 Day / 3 Hour Forecast API Response
 * Endpoint: /forecast
 */
data class ForecastApiResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItem>,
    val message: Int
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class ForecastItem(
    val clouds: Clouds,
    val dt: Int,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Clouds(
    val all: Int
)

data class Main(
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

data class Rain(
    @SerializedName("3h")
    val h: Double
)

data class Sys(
    val pod: String
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)