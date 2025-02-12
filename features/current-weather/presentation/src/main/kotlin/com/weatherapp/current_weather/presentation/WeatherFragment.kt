package com.weatherapp.current_weather.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.core.data.CurrentWeather
import com.core.utils.ResourceProvider
import com.weatherapp.current_weather.domain.models.LastData
import com.weatherapp.current_weather.domain.models.RequestState
import com.weatherapp.current_weather.presentation.databinding.FragmentCurrentWeatherBinding
import com.weatherapp.current_weather.presentation.viewmodel.CurrentWeatherViewModel
import com.weatherapp.current_weather.presentation.viewmodel.CurrentWeatherViewModelFactory
import com.weatherapp.design_system.ErrorView
import com.weatherapp.design_system.LegacyDataView
import com.weatherapp.design_system.LoadingView
import com.weatherapp.design_system.dialogs.PermissionRationaleDialog
import com.weatherapp.design_system.dialogs.PermissionSettingsDialog
import com.weatherapp.design_system.weather_view.WeatherParamsView
import com.weatherapp.current_weather.presentation.di.CurrentWeatherDepsProvider
import kotlinx.coroutines.launch

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel: CurrentWeatherViewModel by viewModels {
        CurrentWeatherViewModelFactory(
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
        observeLastData()
        loadLastData()
        observeWeatherState()
        setOnViewClickListener()
    }

    private fun initDI() {
        val weatherComponent =
            (requireContext().applicationContext as CurrentWeatherDepsProvider).getWeatherComponent()
        weatherComponent.inject(viewModel)
    }

    private fun initPermissionManager() {
        permissionManager = PermissionManager(onPermissionGranted = {
            getCurrentWeather()
        },
            onShowRationaleDialog = { showPermissionRationaleDialog() },
            onShowSettingsDialog = { showSettingsDialog() },
            requireContext()
        )
        permissionManager.initialize(this@WeatherFragment)
        permissionManager.checkAndRequestLocationPermission()
    }

    private fun setOnViewClickListener() {
        binding.refreshButton.setOnClickListener {
            showLoadingView()
            permissionManager.checkAndRequestLocationPermission()
        }
    }

    private fun getCurrentWeather() {
        viewModel.getCurrentWeather()
    }

    private fun loadLastData() {
        viewModel.loadLastData()
    }

    private fun observeWeatherState() {
        lifecycleScope.launch {
            viewModel.weatherState.collect { state ->
                when (state) {
                    is RequestState.ErrorState -> {
                        showToast(state.message)
                    }

                    is RequestState.NormalState -> {
                        showWeatherParamsView(state.data)
                    }
                }
                removeDataStatusViews()
            }
        }
    }

    private fun observeLastData() {
        lifecycleScope.launch {
            viewModel.lastData.collect { state ->
                when (state) {
                    is LastData.CorrectData -> {
                        if (viewModel.isDataLegacy()) showLegacyDataView()
                        showWeatherParamsView(state.data)
                    }

                    is LastData.InvalidData -> {
                        showErrorView(state.message)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLoadingView() {
        removeDataStatusViews()
        binding.dataStatusView.addView(LoadingView(requireContext()))
    }

    private fun showLegacyDataView() {
        removeDataStatusViews()
        binding.dataStatusView.addView(LegacyDataView(requireContext()).apply {
            setOnClickListener {
                showToast(getString(R.string.legacy_data_message))
            }
        })
    }

    private fun showErrorView(message: String) {
        removeWeatherParamsViews()
        removeDataStatusViews()
        val view = ErrorView(requireContext())
        view.setText(message)
        binding.weatherParamsView.addView(view)
        binding.regionWithTempView.setNoneData()
    }

    private fun showWeatherParamsView(data: CurrentWeather) {
        removeWeatherParamsViews()
        binding.weatherParamsView.addView(WeatherParamsView(requireContext()).apply {
            setData(data.current)
        })
        showRegionAndTempData(data)
    }

    private fun showRegionAndTempData(data: CurrentWeather) {
        binding.regionWithTempView.setData(data)
    }

    private fun showPermissionRationaleDialog() {
        PermissionRationaleDialog(onPositiveAction = { permissionManager.requestPermission() },
            onNegativeAction = { showPermissionDeniedMessage() }).show(
            parentFragmentManager, PermissionRationaleDialog.TAG
        )
    }

    private fun showSettingsDialog() {
        PermissionSettingsDialog(onNegativeAction = { showPermissionDeniedMessage() }).show(
            parentFragmentManager, PermissionSettingsDialog.TAG
        )
    }

    private fun showPermissionDeniedMessage() {
        showErrorView(getString(com.weatherapp.design_system.R.string.permission_denied_message))
        removeDataStatusViews()
    }

    private fun removeWeatherParamsViews() {
        binding.weatherParamsView.removeAllViews()
        binding.regionWithTempView.setNoneData()
    }

    private fun removeDataStatusViews() {
        binding.dataStatusView.removeAllViews()
    }
}
