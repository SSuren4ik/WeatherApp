package com.data

import com.core.data.location.LocationData

sealed class LocationRequest {

    data class CorrectLocation(val data: LocationData) : LocationRequest() {
    }

    sealed class Error : LocationRequest() {
        data class ProviderDisabled(val message: String) : Error()
        data class FailGetLocation(val message: String) : Error()
    }
}