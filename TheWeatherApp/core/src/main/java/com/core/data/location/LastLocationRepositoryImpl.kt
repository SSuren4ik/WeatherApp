package com.core.data.location

import com.core.domain.location.LastLocationRepositoryInterface

class LastLocationRepositoryImpl(private val storage: LastLocationStorage) :
    LastLocationRepositoryInterface {

    override fun getLastLocation(): LocationState {
        return storage.loadLocation()
    }

    override fun saveLastLocation(location: LocationState) {
        storage.saveLocation(location)
    }
}
