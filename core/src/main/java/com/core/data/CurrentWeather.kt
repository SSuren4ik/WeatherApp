package com.core.data

data class CurrentWeather(
    val location: ResponseLocation,
    val current: CurrentWeatherData,
)

data class WeatherDataByHours(
    val location: ResponseLocation,
    val current: CurrentWeatherData,
    val forecast: Forecast,
) 

data class ResponseLocation(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)

data class CurrentWeatherData(
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

data class Forecast(
    val forecastday: List<Forecastday>,
)

data class Forecastday(
    val hour: List<Hour>,
)

data class Hour(
    val time: String,
    val temp_c: Double,
    val feelslike_c: Double,
    val is_day: Int,
)