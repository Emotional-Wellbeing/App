package es.upm.bienestaremocional.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.app.data.settings.AppChannels
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.ui.navigation.AppNavigation
import es.upm.bienestaremocional.app.ui.notification.createNotificationChannel
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp(
    appSettings: AppSettingsInterface,
    windowSize: WindowSize,
    healthConnectAvailability: MutableState<HealthConnectAvailability>
)
{

    //init variables
    val navController = rememberNavController()
    val context = LocalContext.current

    if (appSettings.getFirstTimeValue())
    {
        //build channel notifications
        for (appChannel in AppChannels.values())
            createNotificationChannel(
                id = appChannel.channelId,
                name = stringResource(appChannel.nameId),
                importance = appChannel.importance,
                context = context,
            )
        //schedule alarms
        MainApplication.alarmScheduler.setAlarms(appSettings.getAlarmFrequencyValue().alarms)
    }



    //read ui settings
    val darkTheme : ThemeMode = appSettings.getThemeValue()
    val dynamicColors : Boolean = appSettings.getDynamicColorsValue()

    //----------------------------------------------------------------------------------------

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        AppNavigation(
            navController = navController,
            appSettings = appSettings,
            windowSize = windowSize,
            healthConnectAvailability = healthConnectAvailability,
            darkTheme = darkTheme.themeIsDark()
        )
    }
}