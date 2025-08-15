package com.nayibit.phrasalito_presentation.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object NotificationBuilder {

    fun showNotification(context: Context, title: String, message: String) {
        val notificationId = 1
        val channelId = "worker_channel"

        // On Android 13+ check POST_NOTIFICATIONS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permission not granted, skip showing notification
                Log.w("NotificationBuilder", "Notification permission not granted")
                return
            }
        }

        // Create notification channel (API 26+)
        val channel = NotificationChannel(
            channelId,
            "Worker Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel for worker notifications"
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        // Build notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with app icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show notification
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}