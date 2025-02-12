package com.weatherapp.current_weather.data.last_data

import com.weatherapp.current_weather.domain.models.LastData

interface LastDataStorage {
    fun getLastData(): LastData
    fun saveLastData(lastData: LastData.CorrectData)
}