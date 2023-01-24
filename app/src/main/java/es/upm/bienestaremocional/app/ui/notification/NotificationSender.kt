package es.upm.bienestaremocional.app.ui.notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.MainActivity
import es.upm.bienestaremocional.app.ui.navigation.ScreenUri

class NotificationSender(private val context: Context)
{
    //since we don't edit notifications, this code is id to fire and forget
    private var notificationId = 0

    /**
     * Send a notification related to do a questionnaire
     * @see showAccionableNotification
     */
    fun showQuestionnaireNotification()
    {
        val pendingIntent = makePendingIntent(ScreenUri.Questionnaire.uriPattern)

        val textTitle = context.getString(R.string.new_questionnaire_available_title)
        val textContent = context.getString(R.string.new_questionnaire_available_content)

        showAccionableNotification(
            channelId = AppChannels.Questionnaire.channelId,
            textTitle = textTitle,
            textContent = textContent,
            pendingIntent = pendingIntent
        )
    }

    /**
     * Sends a notification that executes a pendingIntent when is pressed
     * @param channelId: Channel where the notification is sent
     * @param textTitle: Title of the notification
     * @param textContent: Content of the notification
     * @param pendingIntent: action to be executed when the user press the notification
     */
    fun showAccionableNotification(
        channelId: String,
        textTitle: String,
        textContent: String,
        pendingIntent: PendingIntent
    )
    {
        val builder = basicBuilder(
            channelId = channelId,
            textTitle = textTitle,
            textContent = textContent
        )

        builder.setStyle(NotificationCompat.BigTextStyle().bigText(textContent))
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)

        showNotification(builder = builder)
    }

    /**
     * Builds a notification with an icon, title and text
     * @param channelId: Channel where the notification is sent
     * @param textTitle: Title of the notification
     * @param textContent: Content of the notification
     */
    private fun basicBuilder(
        channelId: String,
        textTitle: String,
        textContent: String,
    ) : NotificationCompat.Builder
    {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(textTitle)
            .setContentText(textContent)
    }

    /**
     * Shows a notification
     * @param builder: Builder with the notification itself
     */
    private fun showNotification(builder : NotificationCompat.Builder)
    {
        with(NotificationManagerCompat.from(context))
        {
            notify(notificationId++, builder.build())
        }
    }

    /**
     * Obtains a pending intent
     * @param uriPattern: Pattern associated with the related screen of the app
     */
    private fun makePendingIntent(uriPattern : String) : PendingIntent
    {
        val intent = Intent(
            Intent.ACTION_VIEW,
            uriPattern.toUri(),
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(NOTIFICATION_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}