package com.login.domain

import com.login.data.DataState
import com.login.data.UserDataModel

interface UserRepositoryInterface {
    suspend fun signInUser(userDataModel: UserDataModel): DataState
}