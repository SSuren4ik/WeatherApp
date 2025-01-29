package com.domain

sealed class LocationStatus {
    data class LocationCorrect(val location: String) : LocationStatus()
    data class LocationFail(val message: String) : LocationStatus()
}