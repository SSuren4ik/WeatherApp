package com.core.domain.location

import com.core.data.location.LocationState

class SaveLastLocationUseCase(private val repository: LastLocationRepositoryInterface) {

    fun execute(location: LocationState) {
        return repository.saveLastLocation(location)
    }
}