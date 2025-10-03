package com.nayibit.phrasalito_presentation.composables

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.nayibit.phrasalito_presentation.utils.NotificationPermissionManager


@Composable
fun rememberNotificationPermissionHandler(
   onPermissionResult: (Boolean) -> Unit = {},
   onShowRationale: ((onProceed: () -> Unit, onCancel: () -> Unit) -> Unit)? = null
): NotificationPermissionState {

    val context = LocalContext.current
    val permissionManager = remember { NotificationPermissionManager(context) }

    var showSettingsDialog by remember { mutableStateOf(false) }
    var pendingRequest by remember { mutableStateOf(false) }


    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permissionManager.resetDenialCount()
            onPermissionResult(true)
        } else {
            permissionManager.incrementDenialCount()

            if (permissionManager.shouldRedirectToSettings()) {
                showSettingsDialog = true
            }
            onPermissionResult(false)
        }
        pendingRequest = false
    }

    val requestPermission = {

        if (permissionManager.shouldRequestPermission()) {

            if (permissionManager.shouldRedirectToSettings()) {
                showSettingsDialog = true
            } else {
                pendingRequest = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Permission already granted or not needed
            onPermissionResult(permissionManager.isPermissionGranted())
        }
    }

    return NotificationPermissionState(
        isGranted = permissionManager.isPermissionGranted(),
        shouldRequest = permissionManager.shouldRequestPermission(),
        denialCount = permissionManager.getDenialCount(),
        shouldShowSettings = showSettingsDialog,
        isRequestPending = pendingRequest,
        requestPermission = requestPermission,
        openSettings = {
            permissionManager.openAppSettings()
            showSettingsDialog = false
        },
        dismissSettingsDialog = { showSettingsDialog = false }
    )


}

data class NotificationPermissionState(
    val isGranted: Boolean,
    val shouldRequest: Boolean,
    val denialCount: Int,
    val shouldShowSettings: Boolean,
    val isRequestPending: Boolean,
    val requestPermission: () -> Unit,
    val openSettings: () -> Unit,
    val dismissSettingsDialog: () -> Unit
)


