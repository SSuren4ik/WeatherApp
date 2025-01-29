package com.di

import com.core.utils.Router
import com.login.di.LoginModule
import com.utils.RouterImpl
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
        return RouterImpl()
    }
}