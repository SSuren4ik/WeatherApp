package com.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.location.LocationData
import com.core.data.location.LocationState
import com.core.data.network.NetworkRequest
import com.core.domain.location.GetLastLocationUseCase
import com.core.domain.location.SaveLastLocationUseCase
import com.core.domain.network.GetWeatherUseCase
import com.core.domain.network.RequestState
import com.core.utils.ResourceProvider
import com.currentweather.R
import com.data.LocationRequest
import com.domain.GetCurrentLocationUseCase
import com.domain.LocationStatus
import com.permission.PermissionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherViewModel(
    private val provider: ResourceProvider,
) : ViewModel() {

    @Inject
    lateinit var getWeatherUseCase: GetWeatherUseCase

    @Inject
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Inject
    lateinit var getLastLocationUseCase: GetLastLocationUseCase

    @Inject
    lateinit var saveLastLocationUseCase: SaveLastLocationUseCase

    private lateinit var permissionManager: PermissionManager

    private val _weatherState = MutableStateFlow<RequestState>(RequestState.NoRequest)
    val weatherState: StateFlow<RequestState> = _weatherState

    private val _locationState = MutableSharedFlow<Unit>()
    val locationState: SharedFlow<Unit> = _locationState

    private lateinit var lastLocation: LocationState

    private var currentJob: Job? = null

    fun initializeLastLocation(permissionManager: PermissionManager) {
        this.permissionManager = permissionManager
        lastLocation = getLastLocationUseCase.execute()
    }

    fun getWeatherFromLastLocation() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (lastLocation is LocationState.WorldLocation) {
                val locationData = (lastLocation as LocationState.WorldLocation).locationData
                val locationStatus = checkDataLocationCorrect(locationData)
                handleLocationState(locationStatus)
            } else {
                permissionManager.checkAndRequestLocationPermission {
                    getCurrentWeather()
                }
            }
        }
    }

    private suspend fun handleLocationState(locationStatus: LocationStatus) {
        when (locationStatus) {
            is LocationStatus.LocationCorrect -> handleWeatherRequest(locationStatus.location)
            is LocationStatus.LocationFail -> {
                _weatherState.emit(RequestState.ErrorState.UserInputDataError(locationStatus.message))
            }
        }
    }

    fun getCurrentWeather() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            val locationRequest = getCurrentLocationUseCase.execute()
            handleLocationRequest(locationRequest)
        }
    }

    private suspend fun handleLocationRequest(locationRequest: LocationRequest) {
        when (locationRequest) {
            is LocationRequest.CorrectLocation -> {
                handleWeatherRequest(locationRequest.data.toString())
                lastLocation = LocationState.CurrentLocation
            }

            is LocationRequest.Error -> handleLocationError(locationRequest)
        }
    }

    private suspend fun handleWeatherRequest(location: String) {
        val result = withContext(viewModelScope.coroutineContext) {
            getWeatherUseCase.execute(location)
        }
        when (result) {
            is NetworkRequest.NormalConnect -> {
                _weatherState.emit(RequestState.NormalState(result.data))
            }

            is NetworkRequest.ErrorConnect -> {
                val message = getString(com.core.R.string.network_error)
                _weatherState.emit(RequestState.ErrorState.ServerError(message))
            }
        }
    }

    private suspend fun handleLocationError(locationRequest: LocationRequest.Error) {
        val message = when (locationRequest) {
            is LocationRequest.Error.FailGetLocation -> {
                getString(R.string.get_location_error)
            }

            is LocationRequest.Error.ProviderDisabled -> {
                getString(R.string.provider_disabled_error)
            }
        }
        _weatherState.emit(RequestState.ErrorState.ServerError(message))
    }

    fun setLocation(location: LocationState) {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            if (location == lastLocation) {
                return@launch
            }
            when (location) {
                is LocationState.CurrentLocation -> {
                    lastLocation = location
                    _locationState.emit(Unit)
                    saveLastLocationUseCase.execute(location)
                }

                is LocationState.InvalidLocation -> {
                    val message = getString(R.string.input_location_fail_data)
                    val requestState = RequestState.ErrorState.UserInputDataError(message)
                    _weatherState.emit(requestState)
                }

                is LocationState.WorldLocation -> {
                    lastLocation = location
                    _locationState.emit(Unit)
                    saveLastLocationUseCase.execute(location)
                }
            }
        }
    }

    private fun checkDataLocationCorrect(location: LocationData): LocationStatus {
        return if (location.latitude !in -90.0..90.0) {
            LocationStatus.LocationFail(getString(R.string.latitude_fail_data))
        } else if (location.longitude !in -180.0..180.0) {
            LocationStatus.LocationFail(getString(R.string.longitude_fail_data))
        } else {
            LocationStatus.LocationCorrect(location.toString())
        }
    }

    private fun getString(id: Int): String {
        return provider.getString(id)
    }
}
