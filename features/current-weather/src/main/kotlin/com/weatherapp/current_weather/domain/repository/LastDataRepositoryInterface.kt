package com.weatherapp.current_weather.domain.repository

import com.weatherapp.current_weather.data.last_data.LastData

interface LastDataRepositoryInterface {
    suspend fun getLastData(): LastData
    suspend fun saveLastData(data: LastData.CorrectData)
}