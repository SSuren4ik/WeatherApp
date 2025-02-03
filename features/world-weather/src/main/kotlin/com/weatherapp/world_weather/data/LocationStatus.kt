package com.weatherapp.world_weather.data

import com.core.data.LocationData

sealed class LocationStatus {
    data class LocationCorrect(val location: LocationData) : LocationStatus()
    data class LocationFail(val message: String) : LocationStatus()
}