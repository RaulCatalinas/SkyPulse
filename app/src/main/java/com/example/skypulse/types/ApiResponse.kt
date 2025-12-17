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
    @SerializedName("cod")
    val statusCode: String,

    @SerializedName("message")
    val message: Int,

    @SerializedName("cnt")
    val count: Int,

    @SerializedName("list")
    val forecasts: List<ForecastItemDto>,

    @SerializedName("city")
    val city: CityDto
)

data class ForecastItemDto(
    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("main")
    val main: MainDto,

    @SerializedName("weather")
    val weather: List<WeatherDto>,

    @SerializedName("clouds")
    val clouds: CloudsDto,

    @SerializedName("wind")
    val wind: WindDto,

    @SerializedName("visibility")
    val visibility: Int,

    @SerializedName("pop")
    val precipitationProbability: Double,

    @SerializedName("sys")
    val sys: ForecastSysDto,

    @SerializedName("dt_txt")
    val dateTimeText: String,

    @SerializedName("rain")
    val rain: RainDto? = null,

    @SerializedName("snow")
    val snow: SnowDto? = null
)

data class ForecastSysDto(
    @SerializedName("pod")
    val partOfDay: String // "d" for day, "n" for night
)

data class RainDto(
    @SerializedName("3h")
    val threeHours: Double? = null
)

data class SnowDto(
    @SerializedName("3h")
    val threeHours: Double? = null
)

data class CityDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("coord")
    val coordinates: CoordinatesDto,

    @SerializedName("country")
    val country: String,

    @SerializedName("population")
    val population: Int,

    @SerializedName("timezone")
    val timezone: Int,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
)

// ==================== Geocoding API Response ====================

/**
 * City Search Result from Geocoding API
 * Endpoint: /geo/1.0/direct
 */
data class CitySearchDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("lat")
    val latitude: Double,

    @SerializedName("lon")
    val longitude: Double,

    @SerializedName("country")
    val country: String,

    @SerializedName("state")
    val state: String? = null,

    @SerializedName("local_names")
    val localNames: Map<String, String>? = null
)