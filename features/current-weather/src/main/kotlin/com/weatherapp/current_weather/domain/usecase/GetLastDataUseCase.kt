package com.weatherapp.current_weather.domain.usecase

import com.weatherapp.current_weather.data.last_data.LastData
import com.weatherapp.current_weather.domain.repository.LastDataRepositoryInterface

class GetLastDataUseCase(private val locationRepository: LastDataRepositoryInterface) {
    suspend fun execute(): LastData {
        return locationRepository.getLastData()
    }
}