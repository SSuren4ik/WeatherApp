package com.core.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.core.databinding.WeatherParamWithIconViewBinding

class WeatherParamWithIcon(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private var binding: WeatherParamWithIconViewBinding =
        WeatherParamWithIconViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setImageResource(resId: Int) {
        binding.iconImage.setImageResource(resId)
    }

    fun setText(text: String) {
        binding.textView.text = text
    }

    fun setLabel(text: String) {
        binding.labelParaTextView.text = text
    }
}