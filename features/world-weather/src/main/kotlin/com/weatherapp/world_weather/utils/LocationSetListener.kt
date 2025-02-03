package com.weatherapp.world_weather.utils

import com.weatherapp.world_weather.data.LocationState

interface LocationSetListener {
    fun onLocationSet(locationState: LocationState)
}