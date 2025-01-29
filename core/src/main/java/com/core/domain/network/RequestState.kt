package com.core.domain.network

import com.core.data.WeatherData

sealed class RequestState {
    class NormalState(val data: WeatherData) : RequestState()
    sealed class ErrorState : RequestState() {
        data class ServerError(val message: String) : RequestState()
        data class UserInputDataError(val message: String) : RequestState()
    }

    data object NoRequest : RequestState()
}