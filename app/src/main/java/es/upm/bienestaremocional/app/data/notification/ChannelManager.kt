package es.upm.bienestaremocional.app.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager

/**
 * Creates notification channel in order to send notifications properly
 * @param notificationManager: Android service used to create the channel
 * @param channel: Channel to create
 * @see NotificationManager
 * @see NotificationChannel
 * @see NotificationChannels
 */
fun createNotificationChannel(
    notificationManager: NotificationManager,
    channel: NotificationChannels
) = notificationManager.createNotificationChannel(
    NotificationChannel(channel.id, channel.name, channel.importance)
)