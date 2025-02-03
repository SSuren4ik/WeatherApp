package com.weatherapp.current_weather.di

import android.content.Context
import com.core.domain.WeatherService
import com.weatherapp.current_weather.presentation.viewmodel.CurrentWeatherViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CurrentWeatherFeatureModule::class]
)
interface CurrentWeatherComponent {

    val service: WeatherService
    fun inject(viewModel: CurrentWeatherViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): CurrentWeatherComponent
    }
}

interface CurrentWeatherDepsProvider {
    fun getWeatherComponent(): CurrentWeatherComponent
}