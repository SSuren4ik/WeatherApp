package com.utils

import android.content.Context
import android.content.Intent
import com.mainScreen.MainActivity
import com.weatherapp.login.presentation.LoginActivity
import com.weatherapp.login.utils.LoginRouter
import com.weatherapp.splash.utils.SplashRouter

class AppNavigationComponent : SplashRouter, LoginRouter {
    override fun navigateToMainActivity(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    override fun navigateFromSplash(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}