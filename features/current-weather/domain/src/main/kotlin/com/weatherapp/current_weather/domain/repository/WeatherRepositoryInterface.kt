package com.weatherapp.current_weather.domain.repository

import com.weatherapp.current_weather.domain.models.NetworkRequest

interface WeatherRepositoryInterface {
    suspend fun getCurrentWeather(location: String): NetworkRequest
}