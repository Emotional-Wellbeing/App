package es.upm.bienestaremocional.app.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

/**
 * Creates notification channel in order to send notifications properly
 * @see Notification
 */
fun createNotificationChannel(
    id: String,
    name: String,
    importance: Int,
    context: Context)
{
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val notificationChannel = NotificationChannel(id, name, importance)

    notificationManager.createNotificationChannel(notificationChannel)
}