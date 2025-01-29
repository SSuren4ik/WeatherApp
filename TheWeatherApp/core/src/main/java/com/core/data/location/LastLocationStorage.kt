package com.core.data.location

interface LastLocationStorage {
    fun saveLocation(location: LocationState)
    fun loadLocation(): LocationState
}