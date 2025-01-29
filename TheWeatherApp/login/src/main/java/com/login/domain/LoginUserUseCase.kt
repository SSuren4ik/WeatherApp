package com.login.domain

import com.login.data.DataState
import com.login.data.UserDataModel
import javax.inject.Inject

class LoginUserUseCase (private val repository: UserRepositoryInterface) {

    suspend fun execute(userInputDataModel: UserInputDataModel): DataState {
        val userDataModel = UserDataModel(userInputDataModel.login, userInputDataModel.password)
        return repository.signInUser(userDataModel)
    }
}