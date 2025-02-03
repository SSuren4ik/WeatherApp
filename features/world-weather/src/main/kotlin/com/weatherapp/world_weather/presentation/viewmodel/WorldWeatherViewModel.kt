package com.weatherapp.world_weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.LocationData
import com.core.utils.ResourceProvider
import com.weatherapp.world_weather.R
import com.weatherapp.world_weather.data.LocationStatus
import com.weatherapp.world_weather.data.last_location.LastData
import com.weatherapp.world_weather.data.weather.NetworkRequest
import com.weatherapp.world_weather.data.weather.RequestState
import com.weatherapp.world_weather.domain.usecase.GetLastLocationUseCase
import com.weatherapp.world_weather.domain.usecase.GetWeatherByHoursUseCase
import com.weatherapp.world_weather.domain.usecase.SaveLastLocationUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorldWeatherViewModel(
    private val provider: ResourceProvider,
) : ViewModel() {

    @Inject
    lateinit var getWeatherByHoursUseCase: GetWeatherByHoursUseCase

    @Inject
    lateinit var getLastLocationUseCase: GetLastLocationUseCase

    @Inject
    lateinit var saveLastLocationUseCase: SaveLastLocationUseCase

    private val _weatherState = MutableSharedFlow<RequestState>()
    val weatherState: SharedFlow<RequestState> = _weatherState

    private val _lastDataState = MutableSharedFlow<Unit>()
    val lastDataState: SharedFlow<Unit> = _lastDataState

    private var job: Job? = null

    fun getWeather(locationData: LocationData) {
        job?.cancel()
        job = viewModelScope.launch {
            val locationStatus = checkDataLocationCorrect(locationData)
            handleLocationState(locationStatus)
        }
    }

    fun getLastWeatherData() {
        job?.cancel()
        job = viewModelScope.launch {
            val location = getLastLocationUseCase.execute()
            handleLastLocationState(location)
        }
    }

    private suspend fun handleLastLocationState(location: LastData) {
        when (location) {
            is LastData.CorrectData -> _weatherState.emit(RequestState.NormalState(location.data))
            is LastData.InvalidData -> _lastDataState.emit(Unit)
        }
    }

    private suspend fun handleLocationState(locationStatus: LocationStatus) {
        when (locationStatus) {
            is LocationStatus.LocationCorrect -> {
                val result = getWeatherByHoursUseCase.execute(locationStatus.location)
                handleWeatherRequest(result)
            }

            is LocationStatus.LocationFail -> {
                _weatherState.emit(RequestState.ErrorState(locationStatus.message))
            }
        }
    }

    private suspend fun handleWeatherRequest(result: NetworkRequest) {
        when (result) {
            is NetworkRequest.NormalConnect -> {
                saveLastLocationUseCase.execute(result.data)
                _weatherState.emit(RequestState.NormalState(result.data))
            }

            is NetworkRequest.ErrorConnect -> {
                val message = getString(com.weatherapp.design_system.R.string.network_error)
                _weatherState.emit(RequestState.ErrorState(message))
            }
        }
    }

    private fun checkDataLocationCorrect(location: LocationData): LocationStatus {
        return if (location.latitude !in -90.0..90.0) {
            LocationStatus.LocationFail(getString(R.string.latitude_fail_data))
        } else if (location.longitude !in -180.0..180.0) {
            LocationStatus.LocationFail(getString(R.string.longitude_fail_data))
        } else {
            LocationStatus.LocationCorrect(location)
        }
    }

    private fun getString(id: Int): String {
        return provider.getString(id)
    }
}