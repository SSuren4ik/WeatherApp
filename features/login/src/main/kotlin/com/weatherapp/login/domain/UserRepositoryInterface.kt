package com.weatherapp.login.domain

import com.weatherapp.login.data.DataState
import com.weatherapp.login.data.UserDataModel

interface UserRepositoryInterface {
    suspend fun signInUser(userDataModel: UserDataModel): DataState
}