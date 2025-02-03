package com.weatherapp.world_weather.data.weather

import com.core.data.WeatherDataByHours

sealed class NetworkRequest {
    data class NormalConnect(val data: WeatherDataByHours) : NetworkRequest()
    data class ErrorConnect(val message: String) : NetworkRequest()
}