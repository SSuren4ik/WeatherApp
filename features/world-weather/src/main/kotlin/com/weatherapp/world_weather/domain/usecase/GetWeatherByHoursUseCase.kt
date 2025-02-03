package com.weatherapp.world_weather.domain.usecase

import com.core.data.LocationData
import com.weatherapp.world_weather.data.weather.NetworkRequest
import com.weatherapp.world_weather.domain.repository.WeatherByHoursRepositoryInterface

class GetWeatherByHoursUseCase(private val repository: WeatherByHoursRepositoryInterface) {

    suspend fun execute(location: LocationData): NetworkRequest {
        return repository.getWeather(location.toString())
    }
}