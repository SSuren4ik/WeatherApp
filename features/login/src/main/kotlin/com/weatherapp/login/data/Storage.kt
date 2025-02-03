package com.weatherapp.login.data

interface Storage {
    suspend fun getLoginData(): UserDataModel
}