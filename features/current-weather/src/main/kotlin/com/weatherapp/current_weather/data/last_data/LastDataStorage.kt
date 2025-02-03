package com.weatherapp.current_weather.data.last_data

interface LastDataStorage {
    fun getLastData(): LastData
    fun saveLastData(lastData: LastData.CorrectData)
}