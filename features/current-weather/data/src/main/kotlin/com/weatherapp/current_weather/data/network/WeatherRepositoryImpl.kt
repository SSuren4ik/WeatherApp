package com.weatherapp.current_weather.data.network

import com.core.domain.WeatherService
import com.weatherapp.current_weather.domain.models.NetworkRequest
import com.weatherapp.current_weather.domain.repository.WeatherRepositoryInterface
import java.util.Locale

class WeatherRepositoryImpl(
    private var weatherService: WeatherService,
    private var apiKey: String,
) : WeatherRepositoryInterface {

    override suspend fun getCurrentWeather(location: String): NetworkRequest {
        return try {
            val language = Locale.getDefault().language
            val weatherData = weatherService.getCurrentWeather(apiKey, location, language)
            NetworkRequest.NormalConnect(weatherData)
        } catch (e: Exception) {
            NetworkRequest.ErrorConnect("Проблемы с сетью")
        }
    }
}