package com.core.domain.network

import com.core.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("lang") language: String,
    ): WeatherData
}