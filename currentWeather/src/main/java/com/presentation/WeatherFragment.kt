package com.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.core.customView.LoadingView
import com.core.customView.WeatherParamsView
import com.core.data.WeatherData
import com.core.data.location.LocationState
import com.core.domain.network.RequestState
import com.core.utils.ResourceProvider
import com.currentweather.R
import com.currentweather.databinding.FragmentCurrentWeatherBinding
import com.core.customView.ErrorView
import com.customView.PermissionRationaleDialog
import com.customView.PermissionSettingsDialog
import com.di.WeatherDepsProvider
import com.permission.PermissionManager
import com.presentation.viewModel.WeatherViewModel
import com.presentation.viewModel.WeatherViewModelFactory
import com.utils.LocationSetListener
import kotlinx.coroutines.launch

class WeatherFragment : Fragment(), LocationSetListener {

    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(
            requireContext().applicationContext as ResourceProvider
        )
    }
    private lateinit var permissionManager: PermissionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDI()
        initPermissionManager()
        viewModel.initializeLastLocation(permissionManager)
        viewModel.getWeatherFromLastLocation()
        observeLocationChangeState()
        setChangeLocationButtonClickListener()
        observeWeatherState()
    }

    private fun initDI() {
        val weatherComponent =
            (requireContext().applicationContext as WeatherDepsProvider).getWeatherComponent()
        weatherComponent.inject(viewModel)
    }

    private fun initPermissionManager() {
        permissionManager = PermissionManager(
            onPermissionGranted = { getCurrentWeather() },
            onShowRationaleDialog = { showPermissionRationaleDialog() },
            onShowSettingsDialog = { showSettingsDialog() },
            requireContext().applicationContext
        ).apply {
            initialize(this@WeatherFragment)
        }
    }

    private fun getCurrentWeather() {
        showLoadingView()
        viewModel.getCurrentWeather()
    }

    private fun setChangeLocationButtonClickListener() {
        binding.setLocationButton.setOnClickListener {
            val dialog = SetLocationDialogFragment()
            dialog.setLocationSetListener(this)
            dialog.show(parentFragmentManager, "SetLocationDialogFragment")
        }
    }

    private fun observeWeatherState() {
        lifecycleScope.launch {
            viewModel.weatherState.collect { state ->
                when (state) {
                    is RequestState.ErrorState.ServerError -> {
                        showErrorView(state.message)
                    }

                    is RequestState.ErrorState.UserInputDataError -> {
                        showErrorToast(state.message)
                    }

                    is RequestState.NormalState -> showWeatherParamsView(state.data)
                    is RequestState.NoRequest -> showLoadingView()
                }
            }
        }
    }

    private fun observeLocationChangeState() {
        lifecycleScope.launch {
            viewModel.locationState.collect {
                showLoadingView()
            }
        }
    }

    private fun showLoadingView() {
        removeWeatherParamsViews()
        binding.weatherParamsView.addView(LoadingView(requireContext()))
        binding.regionWithTempView.setNoneData()
    }

    private fun showErrorView(message: String) {
        removeWeatherParamsViews()
        val view = ErrorView(requireContext())
        view.setText(message)
        binding.weatherParamsView.addView(view)
        binding.regionWithTempView.setNoneData()
    }

    private fun showWeatherParamsView(data: WeatherData) {
        removeWeatherParamsViews()
        binding.weatherParamsView.addView(WeatherParamsView(requireContext()).apply {
            setWindData(this, data)
            setHumidityData(this, data)
            setPrecipitationData(this, data)
            setTempFeelsLikeData(this, data)
        })
        showRegionAndTempData(data)
    }

    private fun showRegionAndTempData(data: WeatherData) {
        with(binding.regionWithTempView) {
            setRegion(data.location.name)
            setCountry(data.location.country)
            setTemp(data.current.temp_c.toInt().toString())
            setCondition(data.current.condition.text)
        }
    }

    private fun showPermissionRationaleDialog() {
        PermissionRationaleDialog(
            onPositiveAction = { permissionManager.requestPermission() },
            onNegativeAction = { showPermissionDeniedMessage() }
        ).show(parentFragmentManager, PermissionRationaleDialog.TAG)
    }

    private fun showSettingsDialog() {
        PermissionSettingsDialog(
            onNegativeAction = { showPermissionDeniedMessage() }
        ).show(parentFragmentManager, PermissionSettingsDialog.TAG)
    }

    private fun showPermissionDeniedMessage() {
        showErrorView(getString(R.string.permission_denied_message))
    }

    private fun setWindData(view: WeatherParamsView, data: WeatherData) {
        view.setWindText(data.current.wind_kph.toInt())
    }

    private fun setHumidityData(view: WeatherParamsView, data: WeatherData) {
        view.setHumidityText(data.current.humidity)
    }

    private fun setPrecipitationData(view: WeatherParamsView, data: WeatherData) {
        view.setPrecipitationText(data.current.precip_mm.toInt())
    }

    private fun setTempFeelsLikeData(view: WeatherParamsView, data: WeatherData) {
        view.setTempFeelsLikeText(data.current.feelslike_c.toInt())
    }

    private fun removeWeatherParamsViews() {
        binding.weatherParamsView.removeAllViews()
    }

    override fun onLocationSet(locationState: LocationState) {
        when (locationState) {
            is LocationState.WorldLocation -> {
                setLocation(locationState)
                viewModel.getWeatherFromLastLocation()
            }

            is LocationState.CurrentLocation -> {
                permissionManager.checkAndRequestLocationPermission {
                    setLocation(locationState)
                    getCurrentWeather()
                }
            }

            else -> showErrorToast(getString(R.string.input_location_fail_data))
        }
    }

    private fun setLocation(locationState: LocationState) {
        viewModel.setLocation(locationState)
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = WeatherFragment()
    }
}
