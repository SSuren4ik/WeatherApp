package com.login.data

interface Storage{
    suspend fun getLoginData(): UserDataModel
}