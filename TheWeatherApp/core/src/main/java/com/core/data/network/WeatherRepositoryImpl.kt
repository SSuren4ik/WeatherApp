package com.core.data.network

import com.core.domain.network.WeatherRepositoryInterface
import com.core.domain.network.WeatherService
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