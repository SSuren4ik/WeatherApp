package com.weatherapp.world_weather.data.last_location

import com.core.data.WeatherDataByHours

interface LastLocationStorage {
    fun saveLocation(location: WeatherDataByHours)
    fun loadLocation(): LastData
}