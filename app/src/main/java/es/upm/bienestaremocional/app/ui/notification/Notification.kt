package es.upm.bienestaremocional.app.ui.notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.MainActivity
import es.upm.bienestaremocional.app.ui.navigation.ScreenUri

@Composable
fun ShowQuestionnaireNotification(
    context: Context,
    notificationId: Int
)
{
    val pendingIntent = makePendingIntent(context, ScreenUri.Questionnaire.uriPattern)

    val textTitle = stringResource(R.string.new_questionnaire_available_title)
    val textContent = stringResource(R.string.new_questionnaire_available_content)

    showAccionableNotification(
        context = context,
        channelId = AppChannels.Questionnaire.channelId,
        notificationId = notificationId,
        textTitle = textTitle,
        textContent = textContent,
        pendingIntent = pendingIntent
    )
}

fun showAccionableNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    pendingIntent: PendingIntent
) {
    val builder = basicBuilder(
        context = context,
        channelId = channelId,
        textTitle = textTitle,
        textContent = textContent
    )

    builder.setStyle(NotificationCompat.BigTextStyle().bigText(textContent))
    builder.setContentIntent(pendingIntent)
    builder.setAutoCancel(true)

    showNotification(
        context = context,
        notificationId = notificationId,
        builder = builder
    )
}


private fun basicBuilder(
    context: Context,
    channelId: String,
    textTitle: String,
    textContent: String,
) : NotificationCompat.Builder =
    NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.app_logo)
        .setContentTitle(textTitle)
        .setContentText(textContent)

private fun showNotification(
    context: Context,
    notificationId: Int,
    builder : NotificationCompat.Builder
) =
    with(NotificationManagerCompat.from(context))
    {
        notify(notificationId, builder.build())
    }

private fun makePendingIntent(context: Context,
                              uriPattern : String
) : PendingIntent
{
    val intent = Intent(
        Intent.ACTION_VIEW,
        uriPattern.toUri(),
        context,
        MainActivity::class.java
    )
    return TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}