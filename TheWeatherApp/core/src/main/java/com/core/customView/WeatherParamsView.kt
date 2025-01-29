package com.core.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.core.R
import com.core.databinding.WeatherParamsViewBinding

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
        setImages()
        setLabels()
    }

    private fun setLabels() {
        binding.windSpeedView.setLabel(getString(com.core.R.string.wind_label))
        binding.humidityView.setLabel(getString(com.core.R.string.humidity_label))
        binding.precipitationView.setLabel(getString(com.core.R.string.precip_label))
        binding.tempFeelsLikeView.setLabel(getString(com.core.R.string.feels_like_temp_label))
    }

    private fun setImages() {
        binding.windSpeedView.setImageResource(R.drawable.wind)
        binding.humidityView.setImageResource(R.drawable.humidity)
        binding.precipitationView.setImageResource(R.drawable.rain)
        binding.tempFeelsLikeView.setImageResource(R.drawable.feels_like)
    }

    fun setWindText(speed: Int) {
        val text = "$speed " + getString(com.core.R.string.wind_speed_km_h)
        binding.windSpeedView.setText(text)
    }

    fun setHumidityText(humidity: Int) {
        val text = "$humidity " + getString(com.core.R.string.procent)
        binding.humidityView.setText(text)
    }

    fun setPrecipitationText(precip: Int) {
        val text = "$precip " + getString(com.core.R.string.precip_mm)
        binding.precipitationView.setText(text)
    }

    fun setTempFeelsLikeText(temp: Int) {
        val text = "$temp " + getString(com.core.R.string.degree)
        binding.tempFeelsLikeView.setText(text)
    }
}

fun WeatherParamsView.getString(id: Int): String {
    return context.getString(id)
}