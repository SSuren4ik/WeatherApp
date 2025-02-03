package com.weatherapp.design_system.weather_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.core.data.CurrentWeather
import com.weatherapp.design_system.R
import com.weatherapp.design_system.databinding.RegionTempViewBinding

class RegionWithTempView(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private var binding: RegionTempViewBinding =
        RegionTempViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(data: CurrentWeather) {
        setRegion(data.location.name)
        setCountry(data.location.country)
        setTemp(data.current.temp_c.toInt().toString())
        setCondition(data.current.condition.text)
    }

    fun setNoneData() {
        setRegion(getString(R.string.unknown))
        setCountry(getString(R.string.unknown))
        setTemp(getString(R.string.unknown))
        setCondition(getString(R.string.unknown))
    }

    private fun setRegion(region: String) {
        binding.regionTextView.text = region
    }

    private fun setCountry(country: String) {
        binding.countryTextView.text = country
    }

    private fun setTemp(temp: String) {
        if (temp == "-") binding.tempTextView.text = temp
        else binding.tempTextView.text = temp + getString(R.string.degree)
    }

    private fun setCondition(text: String) {
        binding.conditionTextView.text = text
    }
}

fun RegionWithTempView.getString(id: Int): String {
    return context.getString(id)
}