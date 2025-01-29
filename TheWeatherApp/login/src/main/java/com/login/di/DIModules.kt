package com.login.di

import com.login.BuildConfig
import com.login.data.LocalStorageImpl
import com.login.data.Storage
import com.login.data.UserDataModel
import com.login.data.UserRepositoryImpl
import com.login.domain.LoginUserUseCase
import com.login.domain.UserRepositoryInterface
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
