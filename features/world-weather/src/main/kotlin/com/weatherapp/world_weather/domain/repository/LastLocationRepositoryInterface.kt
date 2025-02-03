package com.weatherapp.world_weather.domain.repository

import com.core.data.WeatherDataByHours
import com.weatherapp.world_weather.data.last_location.LastData

interface LastLocationRepositoryInterface {
    suspend fun getLastLocation(): LastData
    suspend fun saveLastLocation(location: WeatherDataByHours)
}