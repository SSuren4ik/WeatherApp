package com.weatherapp.current_weather.domain.models

import com.core.data.LocationData

sealed class LocationRequest {

    data class CorrectLocation(val data: LocationData) : LocationRequest()

    sealed class Error : LocationRequest() {
        data class ProviderDisabled(val message: String) : Error()
        data class FailGetLocation(val message: String) : Error()
    }
}