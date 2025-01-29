package com.core.data.location

sealed class LocationState {
    data object CurrentLocation : LocationState()
    data class WorldLocation(val locationData: LocationData) : LocationState()
    data object InvalidLocation : LocationState()
}