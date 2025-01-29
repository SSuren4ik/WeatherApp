package com.di

import android.content.Context
import com.core.di.LastLocationModule
import com.core.di.NetworkModule
import com.core.di.WeatherModule
import com.data.LocationRepositoryImpl
import com.domain.GetCurrentLocationUseCase
import com.domain.LocationRepositoryInterface
import dagger.Module
import dagger.Provides

@Module(includes = [CurrentLocationModule::class, WeatherModule::class, CurrentLocationModule::class, NetworkModule::class, LastLocationModule::class])
class FeatureModule

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