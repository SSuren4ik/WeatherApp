package com.weatherapp.current_weather.domain.repository

import com.weatherapp.current_weather.domain.models.LastData

interface LastDataRepository {
    suspend fun getLastData(): LastData
    suspend fun saveLastData(data: LastData.CorrectData)
}