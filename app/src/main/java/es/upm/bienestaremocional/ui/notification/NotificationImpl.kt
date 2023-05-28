package es.upm.bienestaremocional.ui.notification

import android.Manifest
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import es.upm.bienestaremocional.MainActivity
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundReduced
import es.upm.bienestaremocional.data.notification.NOTIFICATION_REQUEST_CODE
import es.upm.bienestaremocional.data.notification.NotificationChannels
import es.upm.bienestaremocional.ui.screens.destinations.QuestionnaireRoundScreenDestination
import kotlinx.coroutines.async

class NotificationImpl(private val context: Context,
                       private val logTag : String
) : Notification
{
    //since we don't edit notifications, this code is id to fire and forget
    private var notificationId = 0

    private val taskStackBuilder = TaskStackBuilder.create(context)

    override fun hasNotificationPermission(): Boolean
    {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        else
            true
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    override fun RequestNotificationPermission(onResult : (Boolean) -> Unit)
    {
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = onResult)

        Log.d(logTag,"RequestNotificationPermission")
        LaunchedEffect(true)
        {
            val launchCall = this.async {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            launchCall.await()
        }
    }

    /**
     * Send a notification related to do a questionnaire
     * @see showAccionableNotification
     */
    override fun showQuestionnaireNotification(questionnaireRoundReduced: QuestionnaireRoundReduced)
    {
        val validQRR = QuestionnaireRoundScreenDestination(questionnaireRoundReduced).route
        val pendingIntent = "be://questionnaire/$validQRR".makePendingIntent()

        val textTitle = context.getString(R.string.new_questionnaire_available_title)
        val textContent = context.getString(R.string.new_questionnaire_available_content)

        if(hasNotificationPermission())
            showAccionableNotification(
                channelId = NotificationChannels.Questionnaire.id,
                textTitle = textTitle,
                textContent = textContent,
                pendingIntent = pendingIntent
            )
    }

    override fun showUploadNotification(): android.app.Notification
    {
        val textTitle = context.getString(R.string.uploading)
        val textContent = context.getString(R.string.uploading_in_progress)

        val builder = basicBuilder(
            channelId = NotificationChannels.Main.id,
            textTitle = textTitle,
            textContent = textContent
        ).setOngoing(true)

        return builder.build()
    }

    /**
     * Sends a notification that executes a pendingIntent when is pressed
     * @param channelId: Channel where the notification is sent
     * @param textTitle: Title of the notification
     * @param textContent: Content of the notification
     * @param pendingIntent: action to be executed when the user press the notification
     */
    private fun showAccionableNotification(
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
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId++, builder.build())
        }
    }

    /**
     * Obtains a pending intent from pattern associated with the related screen of the app
     */
    private fun String.makePendingIntent(): PendingIntent
    {
        val intent = Intent(
            Intent.ACTION_VIEW,
            toUri(),
            context,
            MainActivity::class.java
        )
        return taskStackBuilder.run {
            addNextIntentWithParentStack(intent)
            //PendingIntent.FLAG_IMMUTABLE for SDK 31 and above
            getPendingIntent(
                NOTIFICATION_REQUEST_CODE,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
    }
}