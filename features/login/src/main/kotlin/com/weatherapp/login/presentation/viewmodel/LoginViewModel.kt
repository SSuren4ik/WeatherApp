package com.weatherapp.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.login.data.DataState
import com.weatherapp.login.domain.LoginUserUseCase
import com.weatherapp.login.domain.UserInputDataModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    @Inject
    lateinit var loginUserUseCase: LoginUserUseCase

    private val _resultMessage = MutableSharedFlow<DataState>()
    val resultMessage: SharedFlow<DataState> = _resultMessage

    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            val userInputDataModel = UserInputDataModel(login, password)
            val loginResult = loginUserUseCase.execute(userInputDataModel)
            _resultMessage.emit(loginResult)
        }
    }
}