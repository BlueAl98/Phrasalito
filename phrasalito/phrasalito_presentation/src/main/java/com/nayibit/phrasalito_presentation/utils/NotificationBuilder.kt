package com.nayibit.phrasalito_presentation.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
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

        // Create notification channel (API 26+)
        val channel = NotificationChannel(
            channelId,
            "Phrasal Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Daily phrase notifications"
            enableVibration(true)
            setShowBadge(true)
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        // Create custom layout
        val customLayout = RemoteViews(context.packageName, R.layout.notification_custom)

        // Set the content
        customLayout.setTextViewText(R.id.title, phrase)
        customLayout.setTextViewText(R.id.message, translation)

        // Hide example hint if no example provided
        if (example.isNullOrEmpty()) {
            customLayout.setViewVisibility(R.id.example_hint, android.view.View.GONE)
        }

        // Create an intent for when notification is tapped (optional)
        // Replace MainActivity::class.java with your actual main activity
        // val intent = Intent(context, MainActivity::class.java).apply {
        //     flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //     putExtra("phrase", phrase)
        //     putExtra("translation", translation)
        //     putExtra("example", example)
        // }
        //
        // val pendingIntent = PendingIntent.getActivity(
        //     context,
        //     notificationId,
        //     intent,
        //     PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        // )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_noti)
            .setCustomContentView(customLayout)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
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