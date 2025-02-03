package com.weatherapp.world_weather.data.weather

import com.core.data.WeatherDataByHours

sealed class RequestState {
    data class NormalState(val data: WeatherDataByHours) : RequestState()
    data class ErrorState(val message: String) : RequestState()
}