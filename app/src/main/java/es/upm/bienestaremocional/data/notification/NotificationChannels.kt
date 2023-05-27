package es.upm.bienestaremocional.data.notification

import android.app.NotificationManager
import androidx.annotation.StringRes
import es.upm.bienestaremocional.R

/**
 * Enum that contains the notification channels to send notifications to the user
 * @param id: String with the id of the channel
 * @param label: StringResource with the label of the channel to show it
 * @param importance: Int with the Importance of the channel according to Android framework
 * @see NotificationManager
 */
enum class NotificationChannels(val id: String,
                                @StringRes val label: Int,
                                val importance: Int)
{
    Main("0",R.string.main_channel, NotificationManager.IMPORTANCE_DEFAULT),
    Questionnaire("1",R.string.questionnaire_channel, NotificationManager.IMPORTANCE_HIGH)
}