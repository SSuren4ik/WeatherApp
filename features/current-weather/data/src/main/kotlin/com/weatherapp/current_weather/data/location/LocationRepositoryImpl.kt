package com.weatherapp.current_weather.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.core.data.LocationData
import com.weatherapp.current_weather.domain.models.LocationRequest
import com.weatherapp.current_weather.domain.repository.LocationRepositoryInterface
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationRepositoryImpl @Inject constructor(private val context: Context) :
    LocationRepositoryInterface {

    override suspend fun getCurrentLocation(): LocationRequest {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (isLocationProviderDisabled(locationManager)) {
            return LocationRequest.Error.ProviderDisabled(
                "GPS и сети отключены. Включите их для определения местоположения."
            )
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
        val hasFineLocation = hasFineLocation()
        val hasCoarseLocation = hasCoarseLocation()
        val isGpsEnabled = isGPSLocationProviderEnabled(locationManager)
        val isNetworkEnabled = isNetworkLocationProviderEnabled(locationManager)

        when {
            hasFineLocation && isGpsEnabled -> {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L,
                    1f,
                    listener
                )
            }

            hasCoarseLocation && isNetworkEnabled -> {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000L,
                    1f,
                    listener
                )
            }
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

    private fun hasFineLocation(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasCoarseLocation(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
