package com.weatherapp.current_weather.domain.repository

import com.weatherapp.current_weather.data.location.LocationRequest

interface LocationRepositoryInterface {
    suspend fun getCurrentLocation(): LocationRequest
}