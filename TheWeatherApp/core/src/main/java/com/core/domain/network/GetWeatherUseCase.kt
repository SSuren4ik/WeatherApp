package com.core.domain.network

import com.core.data.network.NetworkRequest

class GetWeatherUseCase(private var repositoryInterface: WeatherRepositoryInterface) {
    suspend fun execute(location: String): NetworkRequest {
        return repositoryInterface.getCurrentWeather(location)
    }
}