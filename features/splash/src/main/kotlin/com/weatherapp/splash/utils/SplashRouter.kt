package com.weatherapp.splash.utils

import android.content.Context
import com.core.utils.Router

interface SplashRouter : Router {
    fun navigateFromSplash(context: Context)
}