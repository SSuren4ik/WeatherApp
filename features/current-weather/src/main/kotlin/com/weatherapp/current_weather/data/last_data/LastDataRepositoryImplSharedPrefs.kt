package com.weatherapp.current_weather.data.last_data

import com.weatherapp.current_weather.domain.repository.LastDataRepositoryInterface

class LastDataRepositoryImplSharedPrefs(private val storage: LastDataStorage) :
    LastDataRepositoryInterface {

    override suspend fun getLastData(): LastData {
        return storage.getLastData()
    }

    override suspend fun saveLastData(data: LastData.CorrectData) {
        storage.saveLastData(data)
    }
}