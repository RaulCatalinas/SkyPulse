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
