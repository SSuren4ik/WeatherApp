package com.weatherapp.world_weather.domain.usecase

import com.core.data.WeatherDataByHours
import com.weatherapp.world_weather.domain.repository.LastLocationRepositoryInterface

class SaveLastLocationUseCase(private val repository: LastLocationRepositoryInterface) {

    suspend fun execute(data: WeatherDataByHours) {
        return repository.saveLastLocationData(data)
    }
}