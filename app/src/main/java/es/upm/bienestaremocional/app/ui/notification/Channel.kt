package es.upm.bienestaremocional.app.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import es.upm.bienestaremocional.R

enum class AppChannels(val nameId: Int, val channelId: String, val importance: Int)
{
    Main(R.string.main_channel,"0",NotificationManager.IMPORTANCE_DEFAULT),
    Questionnaire(R.string.questionnaire_channel,"1",NotificationManager.IMPORTANCE_HIGH)
}

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