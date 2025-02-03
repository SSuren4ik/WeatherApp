package com.weatherapp.login.di

import com.weatherapp.login.BuildConfig
import com.weatherapp.login.data.LocalStorageImpl
import com.weatherapp.login.data.Storage
import com.weatherapp.login.data.UserDataModel
import com.weatherapp.login.data.UserRepositoryImpl
import com.weatherapp.login.domain.LoginUserUseCase
import com.weatherapp.login.domain.UserRepositoryInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginModule {

    @Provides
    @Singleton
    fun provideLoginData(): UserDataModel {
        return UserDataModel(BuildConfig.LOGIN, BuildConfig.PASSWORD)
    }

    @Provides
    @Singleton
    fun providesUserRepositoryImpl(storage: Storage): UserRepositoryInterface =
        UserRepositoryImpl(storage)

    @Provides
    @Singleton
    fun providesStorage(userDataModel: UserDataModel): Storage =
        LocalStorageImpl(userDataModel)

    @Provides
    @Singleton
    fun provideLoginUserUseCase(repository: UserRepositoryInterface) = LoginUserUseCase(repository)
}
