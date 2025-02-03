package com.core.domain

import com.core.data.CurrentWeather
import com.core.data.WeatherDataByHours
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("lang") language: String,
    ): CurrentWeather

    @GET("forecast.json")
    suspend fun getWeatherEveryHour(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") language: String,
    ): WeatherDataByHours
}