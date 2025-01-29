package com.core.data

data class WeatherData(
    val location: ResponseLocation,
    val current: CurrentWeather,
)

data class ResponseLocation(
    val name: String,
    val country: String,
)

data class CurrentWeather(
    val temp_c: Double,
    val wind_kph: Double,
    val precip_mm: Double,
    val humidity: Int,
    val condition: Condition,
    val feelslike_c: Double,
)

data class Condition(
    val text: String,
)
