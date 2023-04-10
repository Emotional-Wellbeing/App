package es.upm.bienestaremocional.app.data.notification

import java.time.LocalDateTime

/**
 * Object with the notifications that could be executed
 */
object NotificationsAvailable
{

    val nightNotification = NotificationItem(LocalDateTime.now()
        .withHour(21)
        .withMinute(0)
        .withSecond(0)
        .withNano(0), "night_notification"
    )
    val lunchNotification = NotificationItem(LocalDateTime.now()
        .withHour(14)
        .withMinute(0)
        .withSecond(0)
        .withNano(0), "lunch_notification"
    )

    val debugNotification = NotificationItem(LocalDateTime.now()
        .withHour(16)
        .withMinute(30)
        .withSecond(0)
        .withNano(0), "debug_notification"
    )

    //this is used for cancel all notifications before set new notifications
    val allNotifications = listOf(nightNotification, lunchNotification, debugNotification)
}

