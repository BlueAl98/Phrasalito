package com.nayibit.phrasalito_presentation.screens.startScreen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat

@Composable
@Preview(showBackground = true)
fun StartScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    // States to track permission status
    var hasPermission by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }


  /* LaunchedEffect(key1 = true) {
       hasPermission = ActivityCompat.checkSelfPermission(
           context,
           Manifest.permission.POST_NOTIFICATIONS
       ) == android.content.pm.PackageManager.PERMISSION_GRANTED
   }*/

    // Step 1: Create a permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            hasPermission = true
        } else {
            // Check if we should show rationale or if permission is permanently denied
            val activity = context as androidx.activity.ComponentActivity
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )

            // If shouldShowRationale is false, it means:
            // 1. First time asking (before Android shows dialog), OR
            // 2. User selected "Don't ask again" (permanently denied)
            if (!shouldShowRationale) {
                showSettingsDialog = true
            }
        }
    }

    // Settings Dialog
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Permission Required") },
            text = {
                Text("Notification permission is required. Please enable it in Settings.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Open app settings
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                        showSettingsDialog = false
                    }
                ) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (hasPermission) {
            // Show this when permission is granted
            Text(
                text = "✅ Notification permission granted!",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            // Show this when permission is not granted
            Text(
                text = "We need notification permission",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Step 2: Button to trigger permission request
            Button(
                onClick = {
                    // Step 3: Launch the permission request
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            ) {
                Text("Request Permission")
            }
        }
    }
}