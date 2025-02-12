package com.weatherapp.current_weather.domain.usecase

import com.weatherapp.current_weather.domain.models.LastData
import com.weatherapp.current_weather.domain.repository.LastDataRepository

class GetLastDataUseCase(private val locationRepository: LastDataRepository) {
    suspend fun execute(): LastData {
        return locationRepository.getLastData()
    }
}