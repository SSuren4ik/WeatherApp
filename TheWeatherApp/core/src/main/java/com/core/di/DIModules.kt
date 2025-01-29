package com.core.di

import android.content.Context
import com.core.BuildConfig
import com.core.data.location.LastLocationRepositoryImpl
import com.core.data.location.LastLocationSharedPrefStorage
import com.core.data.location.LastLocationStorage
import com.core.data.network.WeatherRepositoryImpl
import com.core.domain.location.GetLastLocationUseCase
import com.core.domain.location.LastLocationRepositoryInterface
import com.core.domain.location.SaveLastLocationUseCase
import com.core.domain.network.GetWeatherUseCase
import com.core.domain.network.WeatherRepositoryInterface
import com.core.domain.network.WeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class WeatherModule {

    @Provides
    fun provideUserRepository(
        weatherService: WeatherService,
        @Named("apiKey") apiKey: String,
    ): WeatherRepositoryInterface {
        return WeatherRepositoryImpl(weatherService, apiKey)
    }

    @Provides
    fun provideWeatherUseCase(repositoryInterface: WeatherRepositoryInterface): GetWeatherUseCase {
        return GetWeatherUseCase(repositoryInterface)
    }

    @Provides
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY
}

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherService(@Named("baseUrl") baseUrl: String): WeatherService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://api.weatherapi.com/v1/"
}

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
