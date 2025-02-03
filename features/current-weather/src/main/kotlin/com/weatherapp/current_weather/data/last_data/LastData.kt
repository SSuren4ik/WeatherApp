package com.weatherapp.current_weather.data.last_data

import com.core.data.CurrentWeather

sealed class LastData {
    data class CorrectData(val data: CurrentWeather, val time:Int) : LastData()
    data class InvalidData(val message: String) : LastData()
}