package com.application

import android.app.Application
import com.core.utils.ResourceProvider
import com.di.AppComponent
import com.di.DaggerAppComponent
import com.weatherapp.current_weather.di.CurrentWeatherComponent
import com.weatherapp.current_weather.di.CurrentWeatherDepsProvider
import com.weatherapp.current_weather.di.DaggerCurrentWeatherComponent
import com.weatherapp.login.di.DaggerLoginComponent
import com.weatherapp.login.di.LoginComponent
import com.weatherapp.login.di.LoginDepsProvider
import com.weatherapp.splash.di.DaggerSplashComponent
import com.weatherapp.splash.di.SplashComponent
import com.weatherapp.splash.di.SplashDepsProvider
import com.weatherapp.world_weather.di.DaggerWorldWeatherComponent
import com.weatherapp.world_weather.di.WorldWeatherComponent
import com.weatherapp.world_weather.di.WorldWeatherDepsProvider

class CurrentWeatherApplication : Application(), LoginDepsProvider, SplashDepsProvider,
    ResourceProvider,
    CurrentWeatherDepsProvider, WorldWeatherDepsProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun getLoginComponent(): LoginComponent {
        return DaggerLoginComponent.builder().deps(appComponent).build()
    }

    override fun getSplashComponent(): SplashComponent {
        return DaggerSplashComponent.builder().deps(appComponent).build()
    }

    override fun getWeatherComponent(): CurrentWeatherComponent {
        return DaggerCurrentWeatherComponent.builder().context(this).build()
    }

    override fun getWorldWeatherComponent(): WorldWeatherComponent {
        return DaggerWorldWeatherComponent.builder().context(this).build()
    }
}