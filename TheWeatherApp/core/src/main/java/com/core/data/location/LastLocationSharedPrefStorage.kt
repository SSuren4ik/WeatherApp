package com.core.data.location

import android.content.Context
import javax.inject.Inject

class LastLocationSharedPrefStorage @Inject constructor(context: Context) : LastLocationStorage {

    private val sharedPreferences =
        context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    override fun saveLocation(location: LocationState) {
        val editor = sharedPreferences.edit()
        if (location is LocationState.WorldLocation) {
            editor.putFloat("latitude", location.locationData.latitude.toFloat())
            editor.putFloat("longitude", location.locationData.longitude.toFloat())
        } else {
            editor.remove("latitude")
            editor.remove("longitude")
        }
        editor.apply()
    }

    override fun loadLocation(): LocationState {
        val latitude = sharedPreferences.getFloat("latitude", 0f).toDouble()
        val longitude = sharedPreferences.getFloat("longitude", 0f).toDouble()
        return if (latitude != 0.0 && longitude != 0.0) {
            LocationState.WorldLocation(LocationData(latitude, longitude))
        } else {
            LocationState.CurrentLocation
        }
    }
}