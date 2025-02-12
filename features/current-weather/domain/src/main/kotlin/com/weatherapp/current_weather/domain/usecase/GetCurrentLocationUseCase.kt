package com.weatherapp.current_weather.domain.usecase

import com.weatherapp.current_weather.domain.models.LocationRequest
import com.weatherapp.current_weather.domain.repository.LocationRepositoryInterface

class GetCurrentLocationUseCase(private val locationRepository: LocationRepositoryInterface) {
    suspend fun execute(): LocationRequest {
        return locationRepository.getCurrentLocation()
    }
}