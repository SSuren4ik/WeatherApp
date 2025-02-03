package com.weatherapp.world_weather.di

import android.content.Context
import com.core.domain.WeatherService
import com.weatherapp.world_weather.presentation.viewmodel.WorldWeatherViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CurrentWeatherFeatureModule::class]
)
interface WorldWeatherComponent {

    val service: WeatherService
    fun inject(viewModel: WorldWeatherViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): WorldWeatherComponent
    }
}

interface WorldWeatherDepsProvider {
    fun getWorldWeatherComponent(): WorldWeatherComponent
}