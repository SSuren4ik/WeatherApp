package com.weatherapp.current_weather.data.network

import com.core.data.CurrentWeather

sealed class NetworkRequest {
    data class NormalConnect(val data: CurrentWeather) : NetworkRequest()
    data class ErrorConnect(val message: String) : NetworkRequest()
}