package com.application

import android.app.Application
import com.core.utils.ResourceProvider
import com.di.AppComponent
import com.di.DaggerAppComponent
import com.di.DaggerWeatherComponent
import com.di.WeatherComponent
import com.di.WeatherDepsProvider
import com.login.di.DaggerLoginComponent
import com.login.di.LoginComponent
import com.login.di.LoginDepsProvider

class WeatherApplication : Application(), LoginDepsProvider, WeatherDepsProvider, ResourceProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun getLoginComponent(): LoginComponent {
        return DaggerLoginComponent.builder().deps(appComponent).build()
    }

    override fun getWeatherComponent(): WeatherComponent {
        return DaggerWeatherComponent.builder().context(this).build()
    }
}