package com.weatherapp.world_weather.data.last_location

import com.core.data.WeatherDataByHours
import com.weatherapp.world_weather.domain.repository.LastLocationRepositoryInterface

class LastLocationRepositoryImpl(private val storage: LastLocationStorage) :
    LastLocationRepositoryInterface {

    override suspend fun getLastLocation(): LastData {
        return storage.loadLocation()
    }

    override suspend fun saveLastLocation(location: WeatherDataByHours) {
        storage.saveLocation(location)
    }
}