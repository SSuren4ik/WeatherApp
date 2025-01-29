package com.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionManager(
    private val onPermissionGranted: () -> Unit,
    private val onShowRationaleDialog: () -> Unit,
    private val onShowSettingsDialog: () -> Unit,
    private val context: Context,
) {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    fun initialize(activityResultCaller: ActivityResultCaller) {
        requestPermissionLauncher = activityResultCaller.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                onShowSettingsDialog()
            }
        }
    }

    fun checkAndRequestLocationPermission(onPermissionGranted: () -> Unit) {
        when {
            hasLocationPermission() -> onPermissionGranted()
            shouldShowRequestPermissionRationale() -> onShowRationaleDialog()
            else -> requestPermission()
        }
    }

    fun requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun shouldShowRequestPermissionRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            context as FragmentActivity, Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}
