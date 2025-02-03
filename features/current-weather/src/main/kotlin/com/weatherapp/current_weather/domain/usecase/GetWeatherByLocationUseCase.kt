package com.weatherapp.current_weather.domain.usecase

import com.core.data.LocationData
import com.weatherapp.current_weather.data.network.NetworkRequest
import com.weatherapp.current_weather.domain.repository.WeatherRepositoryInterface

class GetWeatherByLocationUseCase(private var repositoryInterface: WeatherRepositoryInterface) {
    suspend fun execute(location: LocationData): NetworkRequest {
        val stringLocation = location.toString()
        return repositoryInterface.getCurrentWeather(stringLocation)
    }
}