package com.core.domain.location

import com.core.data.location.LocationState

class GetLastLocationUseCase(private val repository: LastLocationRepositoryInterface) {

    fun execute(): LocationState {
        return repository.getLastLocation()
    }
}