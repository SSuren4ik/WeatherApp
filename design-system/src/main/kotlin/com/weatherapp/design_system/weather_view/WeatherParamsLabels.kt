package com.weatherapp.design_system.weather_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.weatherapp.design_system.databinding.WeatherParamsLabelsBinding

class WeatherParamsLabels(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    init {
        WeatherParamsLabelsBinding.inflate(LayoutInflater.from(context), this, true)
    }

}

