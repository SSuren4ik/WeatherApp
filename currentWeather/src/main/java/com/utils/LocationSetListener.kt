package com.utils

import com.core.data.location.LocationState

interface LocationSetListener {
    fun onLocationSet(locationState: LocationState)
}