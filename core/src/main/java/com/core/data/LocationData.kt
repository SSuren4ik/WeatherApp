package com.core.data

data class LocationData(val latitude:Double, val longitude:Double) {
    override fun toString(): String {
        return "$latitude, $longitude"
    }
}