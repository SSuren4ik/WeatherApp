package com.core.domain.location

import com.core.data.location.LocationState

interface LastLocationRepositoryInterface {
    fun getLastLocation(): LocationState
    fun saveLastLocation(location: LocationState)
}