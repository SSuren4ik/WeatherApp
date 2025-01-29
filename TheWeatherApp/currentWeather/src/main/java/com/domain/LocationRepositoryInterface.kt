package com.domain

import com.data.LocationRequest

interface LocationRepositoryInterface {
    suspend fun getCurrentLocation(): LocationRequest
}