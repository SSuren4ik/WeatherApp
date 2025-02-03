package com.weatherapp.world_weather.data.last_location

import android.content.Context
import com.core.data.WeatherDataByHours
import com.google.gson.Gson
import javax.inject.Inject

class LastLocationSharedPrefStorage @Inject constructor(context: Context) : LastLocationStorage {

    private val sharedPreferences =
        context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun saveLocation(location: WeatherDataByHours) {
        val json = gson.toJson(location)
        sharedPreferences.edit().putString(DATA_TAG, json).apply()
    }

    override fun loadLocation(): LastData {

        val json = sharedPreferences.getString(DATA_TAG, null)
        var lastData: LastData
        try {
            val data = gson.fromJson(json, WeatherDataByHours::class.java)
            lastData = LastData.CorrectData(data)
        } catch (e: Exception) {
            lastData = LastData.InvalidData("Нужно обновить данные")
        }
        return lastData
    }

    companion object {
        const val DATA_TAG = "world_last_data_tag"
    }
}