package com.weatherapp.world_weather.di

import android.content.Context
import com.core.di.NetworkModule
import com.core.di.WeatherModule
import com.core.domain.WeatherService
import com.weatherapp.world_weather.data.last_location.LastLocationRepositoryImpl
import com.weatherapp.world_weather.data.last_location.LastLocationSharedPrefStorage
import com.weatherapp.world_weather.data.last_location.LastLocationStorage
import com.weatherapp.world_weather.data.weather.WeatherByHoursRepositoryImpl
import com.weatherapp.world_weather.domain.repository.LastLocationRepositoryInterface
import com.weatherapp.world_weather.domain.repository.WeatherByHoursRepositoryInterface
import com.weatherapp.world_weather.domain.usecase.GetLastLocationUseCase
import com.weatherapp.world_weather.domain.usecase.GetWeatherByHoursUseCase
import com.weatherapp.world_weather.domain.usecase.SaveLastLocationUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, WeatherModule::class, LastLocationModule::class, WorldLocationNetworkModule::class])
class CurrentWeatherFeatureModule


@Module
class LastLocationModule {
    @Provides
    @Singleton
    fun provideLastLocationStorage(context: Context): LastLocationStorage {
        return LastLocationSharedPrefStorage(context)
    }

    @Provides
    @Singleton
    fun provideLastLocationRepository(storage: LastLocationStorage): LastLocationRepositoryInterface {
        return LastLocationRepositoryImpl(storage)
    }

    @Provides
    @Singleton
    fun provideGetLastLocationUseCase(repository: LastLocationRepositoryInterface): GetLastLocationUseCase {
        return GetLastLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveLastLocationUseCase(repository: LastLocationRepositoryInterface): SaveLastLocationUseCase {
        return SaveLastLocationUseCase(repository)
    }
}

@Module
class WorldLocationNetworkModule {

    @Provides
    fun provideUserRepository(
        weatherService: WeatherService,
        @Named("apiKey") apiKey: String,
    ): WeatherByHoursRepositoryInterface {
        return WeatherByHoursRepositoryImpl(weatherService, apiKey)
    }

    @Provides
    fun provideWeatherUseCase(repositoryInterface: WeatherByHoursRepositoryInterface): GetWeatherByHoursUseCase {
        return GetWeatherByHoursUseCase(repositoryInterface)
    }
}
