package com.weatherapp.current_weather.domain.usecase

import com.weatherapp.current_weather.domain.models.LastData
import com.weatherapp.current_weather.domain.repository.LastDataRepository

class SaveLastDataUseCase(private val locationRepository: LastDataRepository) {
    suspend fun execute(data: LastData.CorrectData) {
        return locationRepository.saveLastData(data)
    }
}