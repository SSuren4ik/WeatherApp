package com.weatherapp.world_weather.domain.repository

import com.weatherapp.world_weather.data.weather.NetworkRequest

interface WeatherByHoursRepositoryInterface {
    suspend fun getWeather(location: String): NetworkRequest
}