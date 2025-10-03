package com.nayibit.phrasalito_presentation.utils

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat



class NotificationPermissionManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("notification_prefs", Context.MODE_PRIVATE)
    private val DENIAL_COUNT_KEY = "permission_denial_count"
    private val MAX_DENIALS = 3

    fun isPermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            Log.d(TAG, "Permission granted: $granted")
            granted
        } else {
            Log.d(TAG, "Android < 13, permission not needed")
            true
        }
    }

    fun shouldRequestPermission(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPermissionGranted()
    }

    fun getDenialCount(): Int {
        return prefs.getInt(DENIAL_COUNT_KEY, 0)
    }

    fun incrementDenialCount() {
        val currentCount = getDenialCount()
        val newCount = currentCount + 1
        prefs.edit().putInt(DENIAL_COUNT_KEY, newCount).apply()
        Log.d(TAG, "Denial count incremented: $currentCount -> $newCount")
    }

    fun resetDenialCount() {
        prefs.edit().putInt(DENIAL_COUNT_KEY, 0).apply()
        Log.d(TAG, "Denial count reset")
    }

    fun shouldRedirectToSettings(): Boolean {
        return getDenialCount() >= MAX_DENIALS
    }

    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
        Log.d(TAG, "Opening app settings")
    }
}
