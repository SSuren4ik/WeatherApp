package com.di

import com.core.utils.Router
import com.weatherapp.login.di.LoginModule
import com.utils.AppNavigationComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LoginModule::class, UtilsModule::class])
class AppModule

@Module
class UtilsModule {
    @Provides
    @Singleton
    fun provideRouter(): Router {
        return AppNavigationComponent()
    }
}