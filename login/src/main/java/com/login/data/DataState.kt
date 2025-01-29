package com.login.data

sealed class DataState {
    data object NormalState : DataState()
    data class ErrorState(val errorMessage: String) : DataState()
}