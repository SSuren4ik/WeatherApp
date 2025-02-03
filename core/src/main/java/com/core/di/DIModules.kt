package com.core.di

import com.core.BuildConfig
import com.core.domain.WeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class WeatherModule {

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