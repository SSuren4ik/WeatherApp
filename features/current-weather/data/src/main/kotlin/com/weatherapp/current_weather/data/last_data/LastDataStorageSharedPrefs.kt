package com.weatherapp.current_weather.data.last_data

import android.content.Context
import android.content.SharedPreferences
import com.core.data.CurrentWeather
import com.google.gson.Gson
import com.weatherapp.current_weather.domain.models.LastData
import javax.inject.Inject

class LastDataStorageSharedPrefs @Inject constructor(context: Context) : LastDataStorage {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun getLastData(): LastData {
        val json = sharedPreferences.getString(DATA_TAG, null)
        val time = sharedPreferences.getInt(TIME_TAG, 0)
        var lastData: LastData
        try {
            val data = gson.fromJson(json, CurrentWeather::class.java)
            lastData =
                LastData.CorrectData(data, time)
        } catch (e: Exception) {
            lastData =
                LastData.InvalidData("Нужно обновить данные")
        }
        return lastData
    }

    override fun saveLastData(lastData: LastData.CorrectData) {
        val json = gson.toJson(lastData.data)
        sharedPreferences.edit().putString(DATA_TAG, json).apply()
        sharedPreferences.edit().putInt(TIME_TAG, lastData.time).apply()
    }

    companion object {
        const val DATA_TAG = "last_weather_data"
        const val TIME_TAG = "last_weather_time"
    }
}
