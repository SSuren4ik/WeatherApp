package com.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.core.data.location.LocationData
import com.core.data.location.LocationState
import com.currentweather.databinding.DialogSetLocationBinding
import com.customView.DecimalTextWatcher
import com.utils.LocationSetListener

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

        latitudeEditText.addTextChangedListener(DecimalTextWatcher(latitudeEditText))
        longitudeEditText.addTextChangedListener(DecimalTextWatcher(longitudeEditText))

        binding.buttonCurrentLocation.setOnClickListener {
            locationSetListener.onLocationSet(LocationState.CurrentLocation)
            dismiss()
        }

        binding.buttonSetLocation.setOnClickListener {
            val latitude = getLatitude()
            val longitude = getLongitude()
            val locationStatus: LocationState
            if (latitude != null && longitude != null) {
                val locationData = LocationData(latitude, longitude)
                locationStatus = LocationState.WorldLocation(locationData)
            } else {
                locationStatus = LocationState.InvalidLocation
            }
            locationSetListener.onLocationSet(locationStatus)
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
}
