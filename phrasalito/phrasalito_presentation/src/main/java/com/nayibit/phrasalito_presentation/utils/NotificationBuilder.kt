package com.nayibit.phrasalito_presentation.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.nayibit.phrasalito_presentation.R

object NotificationBuilder {

    fun showNotification(
        context: Context,
        phrase: String,
        translation: String,
        example: String? = null
    ) {
        val notificationId = 1
        val channelId = "phrasal_channel"

        // On Android 13+ check POST_NOTIFICATIONS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.w("NotificationBuilder", "Notification permission not granted")
                return
            }
        }

        val channel = NotificationChannel(
            channelId,
            "Phrasal Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Daily phrase notifications"
        }
        context.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)


        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_noti)
            .setContentTitle(null) // 👈 remove system title
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(phrase)
                    .bigText(translation)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setFullScreenIntent(null, true)

        // .setContentIntent(pendingIntent) // Uncomment if you want to handle taps

        // Show notification
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    // Convenience method with your old signature
    fun showNotification(context: Context, title: String, message: String) {
        showNotification(context, title, message, null)
    }
}