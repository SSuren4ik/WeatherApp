package com.core.domain.network

import com.core.data.network.NetworkRequest

interface WeatherRepositoryInterface {
    suspend fun getCurrentWeather(location: String): NetworkRequest
}