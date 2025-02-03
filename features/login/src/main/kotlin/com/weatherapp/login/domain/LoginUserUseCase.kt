package com.weatherapp.login.domain

import com.weatherapp.login.data.DataState
import com.weatherapp.login.data.UserDataModel

class LoginUserUseCase(private val repository: UserRepositoryInterface) {

    suspend fun execute(userInputDataModel: UserInputDataModel): DataState {
        val userDataModel = UserDataModel(userInputDataModel.login, userInputDataModel.password)
        return repository.signInUser(userDataModel)
    }
}