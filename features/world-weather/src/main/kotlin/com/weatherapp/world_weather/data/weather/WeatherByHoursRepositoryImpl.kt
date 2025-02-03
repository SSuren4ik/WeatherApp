package com.weatherapp.world_weather.data.weather

import com.core.domain.WeatherService
import com.weatherapp.world_weather.domain.repository.WeatherByHoursRepositoryInterface
import java.util.Locale

class WeatherByHoursRepositoryImpl(
    private var weatherService: WeatherService,
    private var apiKey: String,
) : WeatherByHoursRepositoryInterface {

    override suspend fun getWeather(location: String): NetworkRequest {
        return try {
            val language = Locale.getDefault().language
            val weatherData = weatherService.getWeatherEveryHour(apiKey, location, 1, language)
            NetworkRequest.NormalConnect(weatherData)
        } catch (e: Exception) {
            NetworkRequest.ErrorConnect("Проблемы с сетью")
        }
    }
}