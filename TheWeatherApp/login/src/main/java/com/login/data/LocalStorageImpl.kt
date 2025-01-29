package com.login.data

class LocalStorageImpl (
    private val userDataModel: UserDataModel
) : Storage {

    override suspend fun getLoginData(): UserDataModel {
        return userDataModel
    }
}