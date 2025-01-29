package com.di

import android.content.Context
import com.core.domain.network.WeatherService
import com.presentation.viewModel.WeatherViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FeatureModule::class]
)
interface WeatherComponent {

    val service: WeatherService
    fun inject(viewModel: WeatherViewModel)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): WeatherComponent
    }
}

interface WeatherDepsProvider {
    fun getWeatherComponent(): WeatherComponent
}