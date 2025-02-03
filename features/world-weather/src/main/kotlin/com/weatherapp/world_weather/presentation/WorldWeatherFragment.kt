package com.weatherapp.world_weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.data.CurrentWeather
import com.core.data.LocationData
import com.core.data.WeatherDataByHours
import com.core.utils.ResourceProvider
import com.weatherapp.world_weather.R
import com.weatherapp.world_weather.data.LocationState
import com.weatherapp.world_weather.data.weather.RequestState
import com.weatherapp.world_weather.databinding.FragmentWorldWeatherBinding
import com.weatherapp.world_weather.di.WorldWeatherDepsProvider
import com.weatherapp.world_weather.presentation.viewmodel.WorldWeatherViewModel
import com.weatherapp.world_weather.presentation.viewmodel.WorldWeatherViewModelFactory
import com.weatherapp.world_weather.utils.LocationSetListener
import kotlinx.coroutines.launch

class WorldWeatherFragment : Fragment(), LocationSetListener {

    private lateinit var binding: FragmentWorldWeatherBinding
    private lateinit var adapter: WeatherDataRecyclerAdapter
    private val viewModel: WorldWeatherViewModel by viewModels {
        WorldWeatherViewModelFactory(
            requireContext().applicationContext as ResourceProvider
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWorldWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDI()
        initRecyclerView()
        setChangeLocationButtonClickListener()
        observeWeatherState()
        observeLastDataState()
        viewModel.getLastWeatherData()
    }

    private fun initDI() {
        val weatherComponent =
            (requireContext().applicationContext as WorldWeatherDepsProvider).getWorldWeatherComponent()
        weatherComponent.inject(viewModel)
    }

    private fun setChangeLocationButtonClickListener() {
        binding.setLocationButton.setOnClickListener {
            val dialog = SetLocationDialogFragment()
            dialog.setLocationSetListener(this)
            dialog.show(parentFragmentManager, SetLocationDialogFragment.TAG)
        }
    }

    private fun initRecyclerView() {
        adapter =
            WeatherDataRecyclerAdapter(requireContext().applicationContext as ResourceProvider)
        binding.weatherDataHolderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.weatherDataHolderRecyclerView.adapter = adapter
    }

    private fun observeWeatherState() {
        lifecycleScope.launch {
            viewModel.weatherState.collect { state ->
                when (state) {
                    is RequestState.ErrorState -> {
                        showToast(state.message)
                    }

                    is RequestState.NormalState -> {
                        updateData(state.data)
                        showRegionAndTempData(state.data)
                    }
                }
            }
        }
    }

    private fun observeLastDataState() {
        lifecycleScope.launch {
            viewModel.lastDataState.collect {
                showLocationHolder()
            }
        }
    }

    private fun showLocationHolder() {
        Toast.makeText(requireContext(), R.string.choose_location_toast_text, Toast.LENGTH_LONG)
            .show()
    }

    private fun getWeather(locationData: LocationData) {
        viewModel.getWeather(locationData)
    }

    private fun updateData(data: WeatherDataByHours) {
        adapter.dataList = data.forecast.forecastday[0].hour
    }

    private fun showRegionAndTempData(data: WeatherDataByHours) {
        val currentWeather = CurrentWeather(data.location, data.current)
        binding.regionWithTempView.setData(currentWeather)
    }

    override fun onLocationSet(locationState: LocationState) {
        when (locationState) {
            is LocationState.Correct -> {
                getWeather(locationState.locationData)
            }

            is LocationState.InvalidLocation -> {
                showToast("Данные введены неверно")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TAG = "WorldWeatherFragment"
    }
}