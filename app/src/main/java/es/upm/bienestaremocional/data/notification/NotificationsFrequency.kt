package es.upm.bienestaremocional.data.notification

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import es.upm.bienestaremocional.R

/**
 * Enum with the options of frequency of notifications that user could set with the associated notifications
 */
enum class NotificationsFrequency(@StringRes val label: Int, val items: List<NotificationItem>)
{
    ONLY_NIGHT(
        label = R.string.questionnaire_only_night,
        items = listOf(
            NotificationsAvailable.nightNotification,
            NotificationsAvailable.debugNotification
        )),
    LUNCH_AND_NIGHT(
        label = R.string.questionnaire_night_lunch,
        items = listOf(
            NotificationsAvailable.nightNotification,
            NotificationsAvailable.lunchNotification,
            NotificationsAvailable.debugNotification
        )
    );

    companion object
    {
        /**
         * Get all [NotificationsFrequency] possible values
         * @return [List] of [NotificationsFrequency] with the values
         */
        fun get(): List<NotificationsFrequency> = values().asList()

        /**
         * Get all labels of the [NotificationsFrequency] possible values
         * @return [List] of [String] with the labels
         */
        @Composable
        fun getLabels(): List<String> = get().map { stringResource(id = it.label)  }
    }
}