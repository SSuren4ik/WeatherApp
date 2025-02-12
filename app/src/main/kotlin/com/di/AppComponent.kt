package com.di

import android.app.Application
import com.core.utils.Router
import com.weatherapp.login.di.LoginDeps
import com.weatherapp.splash.di.SplashDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : LoginDeps, SplashDeps {

    override val router: Router

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
