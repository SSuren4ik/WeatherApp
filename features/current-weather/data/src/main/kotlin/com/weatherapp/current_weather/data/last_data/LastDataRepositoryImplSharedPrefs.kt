package com.weatherapp.current_weather.data.last_data

import com.weatherapp.current_weather.domain.models.LastData
import com.weatherapp.current_weather.domain.repository.LastDataRepository

class LastDataRepositoryImplSharedPrefs(private val storage: LastDataStorage) :
    LastDataRepository {

    override suspend fun getLastData(): LastData {
        return storage.getLastData()
    }

    override suspend fun saveLastData(data: LastData.CorrectData) {
        storage.saveLastData(data)
    }
}