package com.weatherapp.world_weather.data.last_location

import com.core.data.WeatherDataByHours

sealed class LastData {
    data class CorrectData(val data: WeatherDataByHours) : LastData()
    data class InvalidData(val message: String) : LastData()
}