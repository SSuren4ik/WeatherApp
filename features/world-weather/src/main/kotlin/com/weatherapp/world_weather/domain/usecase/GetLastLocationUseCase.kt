package com.weatherapp.world_weather.domain.usecase

import com.weatherapp.world_weather.data.last_location.LastData
import com.weatherapp.world_weather.domain.repository.LastLocationRepositoryInterface

class GetLastLocationUseCase(private val repository: LastLocationRepositoryInterface) {
    suspend fun execute(): LastData {
        return repository.getLastLocationData()
    }
}