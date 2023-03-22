package es.upm.bienestaremocional.app

import android.app.NotificationManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.notification.NotificationChannels
import es.upm.bienestaremocional.app.data.notification.createNotificationChannel
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.ui.screens.NavGraphs
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp(appSettings: AppSettings,
                          scheduler: AlarmScheduler)
{

    //init variables
    val context = LocalContext.current
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (appSettings.getFirstTimeValue())
    {
        //build channel notifications
        for (appChannel in NotificationChannels.values())
            createNotificationChannel(
                notificationManager = notificationManager,
                channel = appChannel
            )
        //schedule alarms
        scheduler.schedule(appSettings.getAlarmFrequencyValue().alarmItems)
    }

    //read ui settings
    val darkTheme : ThemeMode = appSettings.getThemeValue()
    val dynamicColors : Boolean = appSettings.getDynamicColorsValue()

    //----------------------------------------------------------------------------------------

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}