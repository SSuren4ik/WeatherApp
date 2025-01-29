package com.core.data.location

data class LocationData(val latitude:Double, val longitude:Double) {
    override fun toString(): String {
        return "$latitude, $longitude"
    }
}