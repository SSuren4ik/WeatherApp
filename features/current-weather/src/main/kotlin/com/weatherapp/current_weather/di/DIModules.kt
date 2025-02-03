package com.weatherapp.current_weather.di

import android.content.Context
import com.core.di.NetworkModule
import com.core.di.WeatherModule
import com.core.domain.WeatherService
import com.weatherapp.current_weather.data.last_data.LastDataRepositoryImplSharedPrefs
import com.weatherapp.current_weather.data.last_data.LastDataStorage
import com.weatherapp.current_weather.data.last_data.LastDataStorageSharedPrefs
import com.weatherapp.current_weather.data.location.LocationRepositoryImpl
import com.weatherapp.current_weather.data.network.WeatherRepositoryImpl
import com.weatherapp.current_weather.domain.repository.LastDataRepositoryInterface
import com.weatherapp.current_weather.domain.repository.LocationRepositoryInterface
import com.weatherapp.current_weather.domain.repository.WeatherRepositoryInterface
import com.weatherapp.current_weather.domain.usecase.GetCurrentLocationUseCase
import com.weatherapp.current_weather.domain.usecase.GetLastDataUseCase
import com.weatherapp.current_weather.domain.usecase.GetWeatherByLocationUseCase
import com.weatherapp.current_weather.domain.usecase.SaveLastDataUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [CurrentLocationModule::class, CurrentLocationNetworkModule::class, LastDataModule::class, WeatherModule::class, NetworkModule::class]
)
class CurrentWeatherFeatureModule

@Module
class CurrentLocationModule {

    @Provides
    fun provideLocationRepository(
        context: Context,
    ): LocationRepositoryInterface {
        return LocationRepositoryImpl(context)
    }

    @Provides
    fun provideLocationUseCase(locationRepositoryInterface: LocationRepositoryInterface): GetCurrentLocationUseCase {
        return GetCurrentLocationUseCase(locationRepositoryInterface)
    }
}

@Module
class CurrentLocationNetworkModule {

    @Provides
    fun provideUserRepository(
        weatherService: WeatherService,
        @Named("apiKey") apiKey: String,
    ): WeatherRepositoryInterface {
        return WeatherRepositoryImpl(weatherService, apiKey)
    }

    @Provides
    fun provideWeatherUseCase(repositoryInterface: WeatherRepositoryInterface): GetWeatherByLocationUseCase {
        return GetWeatherByLocationUseCase(repositoryInterface)
    }
}

@Module
class LastDataModule {
    @Provides
    @Singleton
    fun provideLastDataStorage(context: Context): LastDataStorage {
        return LastDataStorageSharedPrefs(context)
    }

    @Provides
    @Singleton
    fun provideLastDataRepository(storage: LastDataStorage): LastDataRepositoryInterface {
        return LastDataRepositoryImplSharedPrefs(storage)
    }

    @Provides
    @Singleton
    fun provideGetLastDataUseCase(repository: LastDataRepositoryInterface): GetLastDataUseCase {
        return GetLastDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveLastDataUseCase(repository: LastDataRepositoryInterface): SaveLastDataUseCase {
        return SaveLastDataUseCase(repository)
    }

}