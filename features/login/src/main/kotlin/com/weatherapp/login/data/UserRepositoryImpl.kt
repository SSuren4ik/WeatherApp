package com.weatherapp.login.data

import com.weatherapp.login.domain.UserRepositoryInterface

class UserRepositoryImpl(
    private val storage: Storage,
) : UserRepositoryInterface {

    override suspend fun signInUser(userDataModel: UserDataModel): DataState {
        val correctLoginData = storage.getLoginData()

        return if (correctLoginData.login == userDataModel.login && correctLoginData.password == userDataModel.password) {
            DataState.NormalState
        } else {
            DataState.ErrorState("Invalid login or password")
        }
    }
}