package com.core.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.core.R
import com.core.databinding.RegionTempViewBinding

class RegionWithTempView(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private var binding: RegionTempViewBinding =
        RegionTempViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setRegion(region: String) {
        binding.regionTextView.text = region
    }

    fun setCountry(country: String) {
        binding.countryTextView.text = country
    }

    fun setTemp(temp: String) {
        if (temp == "-") binding.tempTextView.text = temp
        else binding.tempTextView.text = temp + getString(R.string.degree)
    }

    fun setCondition(text: String) {
        binding.conditionTextView.text = text
    }

    fun setNoneData() {
        setRegion(getString(R.string.unknown))
        setCountry(getString(R.string.unknown))
        setTemp(getString(R.string.unknown))
        setCondition(getString(R.string.unknown))
    }
}

fun RegionWithTempView.getString(id: Int): String {
    return context.getString(id)
}