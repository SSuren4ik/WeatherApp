package com.core.data.network

import com.core.data.WeatherData

sealed class NetworkRequest {

    data class NormalConnect(val data: WeatherData) : NetworkRequest()
    data class ErrorConnect(val message: String) : NetworkRequest()
}