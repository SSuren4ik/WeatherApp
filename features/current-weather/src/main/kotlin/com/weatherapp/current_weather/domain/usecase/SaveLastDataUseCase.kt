package com.weatherapp.current_weather.domain.usecase

import com.weatherapp.current_weather.data.last_data.LastData
import com.weatherapp.current_weather.domain.repository.LastDataRepositoryInterface

class SaveLastDataUseCase(private val locationRepository: LastDataRepositoryInterface) {
    suspend fun execute(data: LastData.CorrectData) {
        return locationRepository.saveLastData(data)
    }
}