package com.weatherapp.design_system

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.weatherapp.design_system.databinding.LegacyDataViewBinding

class LegacyDataView(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    init {
        LegacyDataViewBinding.inflate(LayoutInflater.from(context), this, true)
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
    }
}