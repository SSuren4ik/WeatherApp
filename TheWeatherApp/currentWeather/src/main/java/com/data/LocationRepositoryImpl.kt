package com.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import com.core.data.location.LocationData
import com.domain.LocationRepositoryInterface
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(val context: Context) : LocationRepositoryInterface {

    override suspend fun getCurrentLocation(): LocationRequest {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (isLocationProviderDisabled(locationManager)) {
            return LocationRequest.Error.ProviderDisabled("GPS и сети отключены. Включите их для определения местоположения.")
        }

        return try {
            val location = getLocation(locationManager)
            val data = LocationData(location.latitude, location.longitude)
            LocationRequest.CorrectLocation(data)
        } catch (e: Exception) {
            LocationRequest.Error.FailGetLocation("Ошибка получения местоположения: ${e.message}")
        }
    }

    private suspend fun getLocation(locationManager: LocationManager): Location =
        suspendCancellableCoroutine { continuation ->
            val locationListener = createLocationListener(continuation, locationManager)
            registerLocationUpdates(locationManager, locationListener)
        }

    @SuppressLint("MissingPermission")
    private fun registerLocationUpdates(
        locationManager: LocationManager,
        listener: LocationListener,
    ) {
        if (isGPSLocationProviderEnabled(locationManager)) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0L, 0f, listener
            )
        } else if (isNetworkLocationProviderEnabled(locationManager)) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0L, 0f, listener
            )
        }
    }

    private fun createLocationListener(
        continuation: CancellableContinuation<Location>,
        locationManager: LocationManager,
    ): LocationListener {
        var isResultDelivered = false
        return object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (!isResultDelivered) {
                    isResultDelivered = true
                    continuation.resume(location)
                    locationManager.removeUpdates(this)
                }
            }

            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    }

    private fun isLocationProviderDisabled(locationManager: LocationManager): Boolean {
        val isGpsEnabled = isGPSLocationProviderEnabled(locationManager)
        val isNetworkEnabled = isNetworkLocationProviderEnabled(locationManager)
        return !isGpsEnabled && !isNetworkEnabled
    }

    private fun isGPSLocationProviderEnabled(locationManager: LocationManager): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun isNetworkLocationProviderEnabled(locationManager: LocationManager): Boolean {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
