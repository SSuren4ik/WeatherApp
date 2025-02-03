package com.weatherapp.splash.di

import com.core.utils.Router
import com.weatherapp.splash.presentation.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [SplashDeps::class])
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)

    @Component.Builder
    interface Builder {
        fun deps(deps: SplashDeps): Builder
        fun build(): SplashComponent
    }
}

interface SplashDeps {
    val router: Router
}

interface SplashDepsProvider {
    fun getSplashComponent(): SplashComponent
}