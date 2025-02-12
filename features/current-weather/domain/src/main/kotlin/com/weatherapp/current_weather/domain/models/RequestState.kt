package com.weatherapp.current_weather.domain.models

import com.core.data.CurrentWeather

sealed class RequestState {
    data class NormalState(val data: CurrentWeather) : RequestState()
    data class ErrorState(val message: String) : RequestState()
}