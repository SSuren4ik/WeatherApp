package com.weatherapp.design_system.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.weatherapp.design_system.R

class PermissionRationaleDialog(
    private val onPositiveAction: () -> Unit,
    private val onNegativeAction: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.permission_required_title)
            .setMessage(R.string.permission_required_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                onPositiveAction()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                onNegativeAction()
            }
            .create()
    }

    companion object {
        const val TAG = "PermissionRationaleDialog"
    }
}
