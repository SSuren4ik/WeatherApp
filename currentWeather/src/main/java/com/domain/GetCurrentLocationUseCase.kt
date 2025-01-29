package com.domain

import com.data.LocationRequest

class GetCurrentLocationUseCase(private val locationRepository: LocationRepositoryInterface) {
    suspend fun execute(): LocationRequest {
        return locationRepository.getCurrentLocation()
    }
}