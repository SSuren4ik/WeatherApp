package com.weatherapp.current_weather.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.data.LocationData
import com.core.utils.ResourceProvider
import com.weatherapp.current_weather.R
import com.weatherapp.current_weather.data.last_data.LastData
import com.weatherapp.current_weather.data.location.LocationRequest
import com.weatherapp.current_weather.data.network.NetworkRequest
import com.weatherapp.current_weather.data.network.RequestState
import com.weatherapp.current_weather.domain.usecase.GetCurrentLocationUseCase
import com.weatherapp.current_weather.domain.usecase.GetLastDataUseCase
import com.weatherapp.current_weather.domain.usecase.GetWeatherByLocationUseCase
import com.weatherapp.current_weather.domain.usecase.SaveLastDataUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class CurrentWeatherViewModel(
    private val provider: ResourceProvider,
) : ViewModel() {

    @Inject
    lateinit var getWeatherByLocationUseCase: GetWeatherByLocationUseCase

    @Inject
    lateinit var getCurrentLocationUseCase: GetCurrentLocationUseCase

    @Inject
    lateinit var getLastDataUseCase: GetLastDataUseCase

    @Inject
    lateinit var saveLastDataUseCase: SaveLastDataUseCase

    private val _weatherState = MutableSharedFlow<RequestState>()
    val weatherState: SharedFlow<RequestState> = _weatherState

    private val _lastData = MutableSharedFlow<LastData>()
    val lastData: SharedFlow<LastData> = _lastData

    private var isDataLegacy = false

    fun getCurrentWeather() {
        viewModelScope.launch {
            val locationRequest = getCurrentLocationUseCase.execute()
            handleLocationRequest(locationRequest)
        }
    }

    private suspend fun handleLocationRequest(locationRequest: LocationRequest) {
        when (locationRequest) {
            is LocationRequest.CorrectLocation -> {
                handleWeatherRequest(locationRequest.data)
            }

            is LocationRequest.Error -> {
                handleLocationError(locationRequest)
            }
        }
    }

    private suspend fun handleWeatherRequest(location: LocationData) {
        val result = withContext(viewModelScope.coroutineContext) {
            getWeatherByLocationUseCase.execute(location)
        }
        when (result) {
            is NetworkRequest.NormalConnect -> {
                val currentTime =
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond().toInt()
                val saveData = LastData.CorrectData(result.data, currentTime)
                saveLastDataUseCase.execute(saveData)
                _weatherState.emit(RequestState.NormalState(result.data))
            }

            is NetworkRequest.ErrorConnect -> {
                val message = getString(com.weatherapp.design_system.R.string.network_error)
                _weatherState.emit(RequestState.ErrorState(message))
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
        _weatherState.emit(RequestState.ErrorState(message))
    }

    private fun getString(id: Int): String {
        return provider.getString(id)
    }

    fun loadLastData() {
        viewModelScope.launch {
            when (val result = getLastDataUseCase.execute()) {
                is LastData.CorrectData -> {
                    val currentTime = getCurrentTime()
                    val timeDifference = currentTime - result.time
                    isDataLegacy = timeDifference > 3600
                    _lastData.emit(result)
                }

                is LastData.InvalidData -> {
                    val message = getString(R.string.none_legacy_message)
                    _lastData.emit(LastData.InvalidData(message))
                }
            }
        }
    }

    private fun getCurrentTime(): Int {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond().toInt()
    }

    fun isDataLegacy() = isDataLegacy
}
