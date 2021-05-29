package nnar.learning_app.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import nnar.learning_app.R

class NotificationUtil {

    private val channelID = "mobile_notifications_1"

    fun createNotificationChannel(context: Context): String {
        /** Create the NotificationChannel, but only on API 26+ because
        the NotificationChannel class is new and not in the support library */
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
            }
            /** Register the channel with the system */
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            channel.id
        }
        else ""
    }


}