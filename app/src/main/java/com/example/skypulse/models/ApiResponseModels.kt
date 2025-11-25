package com.example.skypulse.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Current Weather API Response
 * Endpoint: /weather
 */
@Serializable
data class WeatherApiResponse(
    @SerialName("coord")
    val coordinates: CoordinatesDto,

    @SerialName("weather")
    val weather: List<WeatherDto>,

    @SerialName("base")
    val base: String,

    @SerialName("main")
    val main: MainDto,

    @SerialName("visibility")
    val visibility: Int,

    @SerialName("wind")
    val wind: WindDto,

    @SerialName("clouds")
    val clouds: CloudsDto,

    @SerialName("dt")
    val timestamp: Long,

    @SerialName("sys")
    val sys: SysDto,

    @SerialName("timezone")
    val timezone: Int,

    @SerialName("id")
    val cityId: Int,

    @SerialName("name")
    val cityName: String,

    @SerialName("cod")
    val statusCode: Int
)

@Serializable
data class CoordinatesDto(
    @SerialName("lon")
    val longitude: Double,

    @SerialName("lat")
    val latitude: Double
)

@Serializable
data class WeatherDto(
    @SerialName("id")
    val id: Int,

    @SerialName("main")
    val main: String,

    @SerialName("description")
    val description: String,

    @SerialName("icon")
    val icon: String
)

@Serializable
data class MainDto(
    @SerialName("temp")
    val temperature: Double,

    @SerialName("feels_like")
    val feelsLike: Double,

    @SerialName("temp_min")
    val tempMin: Double,

    @SerialName("temp_max")
    val tempMax: Double,

    @SerialName("pressure")
    val pressure: Int,

    @SerialName("humidity")
    val humidity: Int,

    @SerialName("sea_level")
    val seaLevel: Int? = null,

    @SerialName("grnd_level")
    val groundLevel: Int? = null
)

@Serializable
data class WindDto(
    @SerialName("speed")
    val speed: Double,

    @SerialName("deg")
    val degrees: Int,

    @SerialName("gust")
    val gust: Double? = null
)

@Serializable
data class CloudsDto(
    @SerialName("all")
    val cloudiness: Int
)

@Serializable
data class SysDto(
    @SerialName("type")
    val type: Int? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("country")
    val country: String,

    @SerialName("sunrise")
    val sunrise: Long,

    @SerialName("sunset")
    val sunset: Long
)

// ==================== 5 Day Forecast API Response ====================

/**
 * 5 Day / 3 Hour Forecast API Response
 * Endpoint: /forecast
 */
@Serializable
data class ForecastApiResponse(
    @SerialName("cod")
    val statusCode: String,

    @SerialName("message")
    val message: Int,

    @SerialName("cnt")
    val count: Int,

    @SerialName("list")
    val forecasts: List<ForecastItemDto>,

    @SerialName("city")
    val city: CityDto
)

@Serializable
data class ForecastItemDto(
    @SerialName("dt")
    val timestamp: Long,

    @SerialName("main")
    val main: MainDto,

    @SerialName("weather")
    val weather: List<WeatherDto>,

    @SerialName("clouds")
    val clouds: CloudsDto,

    @SerialName("wind")
    val wind: WindDto,

    @SerialName("visibility")
    val visibility: Int,

    @SerialName("pop")
    val precipitationProbability: Double,

    @SerialName("sys")
    val sys: ForecastSysDto,

    @SerialName("dt_txt")
    val dateTimeText: String,

    @SerialName("rain")
    val rain: RainDto? = null,

    @SerialName("snow")
    val snow: SnowDto? = null
)

@Serializable
data class ForecastSysDto(
    @SerialName("pod")
    val partOfDay: String // "d" for day, "n" for night
)

@Serializable
data class RainDto(
    @SerialName("3h")
    val threeHours: Double? = null
)

@Serializable
data class SnowDto(
    @SerialName("3h")
    val threeHours: Double? = null
)

@Serializable
data class CityDto(
    @SerialName("id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("coord")
    val coordinates: CoordinatesDto,

    @SerialName("country")
    val country: String,

    @SerialName("population")
    val population: Int,

    @SerialName("timezone")
    val timezone: Int,

    @SerialName("sunrise")
    val sunrise: Long,

    @SerialName("sunset")
    val sunset: Long
)

// ==================== Geocoding API Response ====================

/**
 * City Search Result from Geocoding API
 * Endpoint: /geo/1.0/direct
 */
@Serializable
data class CitySearchDto(
    @SerialName("name")
    val name: String,

    @SerialName("lat")
    val latitude: Double,

    @SerialName("lon")
    val longitude: Double,

    @SerialName("country")
    val country: String,

    @SerialName("state")
    val state: String? = null,

    @SerialName("local_names")
    val localNames: Map<String, String>? = null
)