package nnar.learning_app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class NotificationChannel {
    private val channel_id = "channel_id_1"

    fun createNotificationChannel(context: Context): String {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //val name = getString(channel_name)
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            //val descriptionText = getString(
            //    channel_name
            //)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channel_id, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            channel.id
        }
        else ""
    }
}