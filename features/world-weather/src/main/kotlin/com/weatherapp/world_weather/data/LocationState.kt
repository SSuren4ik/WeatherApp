package com.weatherapp.world_weather.data

import com.core.data.LocationData

sealed class LocationState {
    data class Correct(val locationData: LocationData) : LocationState()
    data object InvalidLocation : LocationState()
}