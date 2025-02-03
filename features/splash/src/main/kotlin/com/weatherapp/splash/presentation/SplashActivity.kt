package com.weatherapp.splash.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.core.utils.Router
import com.weatherapp.splash.databinding.ActivitySplashBinding
import com.weatherapp.splash.di.SplashComponent
import com.weatherapp.splash.di.SplashDepsProvider
import com.weatherapp.splash.utils.SplashRouter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    private val splashComponent: SplashComponent by lazy {
        (applicationContext as SplashDepsProvider).getSplashComponent()
    }

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        splashComponent.inject(this)
        lifecycleScope.launch {
            delay(1500L)
            (router as SplashRouter).navigateFromSplash(this@SplashActivity)
            finish()
        }
    }

}