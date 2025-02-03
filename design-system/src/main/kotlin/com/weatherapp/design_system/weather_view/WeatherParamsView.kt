package com.weatherapp.design_system.weather_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.core.data.CurrentWeatherData
import com.weatherapp.design_system.R
import com.weatherapp.design_system.databinding.WeatherParamsViewBinding

class WeatherParamsView(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private var binding: WeatherParamsViewBinding =
        WeatherParamsViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        setLabels()
        setImages()
    }

    private fun setLabels() {
        binding.windSpeedView.setLabel(getString(R.string.wind_label))
        binding.humidityView.setLabel(getString(R.string.humidity_label))
        binding.precipitationView.setLabel(getString(R.string.precip_label))
        binding.tempFeelsLikeView.setLabel(getString(R.string.feels_like_temp_label))
    }

    private fun setImages() {
        binding.windSpeedView.setImageResource(R.drawable.wind)
        binding.humidityView.setImageResource(R.drawable.humidity)
        binding.precipitationView.setImageResource(R.drawable.rain)
        binding.tempFeelsLikeView.setImageResource(R.drawable.feels_like)
    }

    fun setData(data: CurrentWeatherData) {
        setWindText(data.wind_kph.toInt())
        setHumidityText(data.humidity)
        setPrecipitationText(data.precip_mm.toInt())
        setTempFeelsLikeText(data.feelslike_c.toInt())
    }

    private fun setWindText(speed: Int) {
        val text = "$speed " + getString(R.string.wind_speed_km_h)
        binding.windSpeedView.setText(text)
    }

    private fun setHumidityText(humidity: Int) {
        val text = "$humidity " + getString(R.string.procent)
        binding.humidityView.setText(text)
    }

    private fun setPrecipitationText(precip: Int) {
        val text = "$precip " + getString(R.string.precip_mm)
        binding.precipitationView.setText(text)
    }

    private fun setTempFeelsLikeText(temp: Int) {
        val text = "$temp " + getString(R.string.degree)
        binding.tempFeelsLikeView.setText(text)
    }
}

fun WeatherParamsView.getString(id: Int): String {
    return context.getString(id)
}