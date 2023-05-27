package es.upm.bienestaremocional.data.notification

import java.time.LocalDateTime

/**
 * Class with the data of each possible notification
 * @param time When a notification should be shown
 * @param tag Tag of this item to identify it
 * @see NotificationsAvailable
 */
data class NotificationItem(val time: LocalDateTime, val tag: String)