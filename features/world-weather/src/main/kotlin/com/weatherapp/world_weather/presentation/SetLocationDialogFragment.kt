package com.weatherapp.world_weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.core.data.LocationData
import com.weatherapp.world_weather.data.LocationState
import com.weatherapp.world_weather.databinding.DialogSetLocationBinding
import com.weatherapp.world_weather.utils.DecimalTextWatcher
import com.weatherapp.world_weather.utils.LocationSetListener

class SetLocationDialogFragment : DialogFragment() {

    private lateinit var binding: DialogSetLocationBinding
    private lateinit var locationSetListener: LocationSetListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogSetLocationBinding.inflate(inflater, container, false)

        val latitudeEditText = binding.latitudeEditText
        val longitudeEditText = binding.longitudeEditText

        latitudeEditText.addTextChangedListener(
            DecimalTextWatcher(
                latitudeEditText
            )
        )
        longitudeEditText.addTextChangedListener(
            DecimalTextWatcher(
                longitudeEditText
            )
        )

        binding.buttonSetLocation.setOnClickListener {
            val latitude = getLatitude()
            val longitude = getLongitude()
            val locationState: LocationState
            if (latitude != null && longitude != null) {
                val locationData = LocationData(latitude, longitude)
                locationState = LocationState.Correct(locationData)
            } else {
                locationState = LocationState.InvalidLocation
            }
            locationSetListener.onLocationSet(locationState)
            dismiss()
        }

        return binding.root
    }

    private fun getLatitude(): Double? {
        return binding.latitudeEditText.text.toString().toDoubleOrNull()
    }

    private fun getLongitude(): Double? {
        return binding.longitudeEditText.text.toString().toDoubleOrNull()
    }

    fun setLocationSetListener(listener: LocationSetListener) {
        this.locationSetListener = listener
    }

    companion object {
        const val TAG = "SetLocationDialogFragment"
    }
}
