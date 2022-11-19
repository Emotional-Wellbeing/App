package es.upm.bienestaremocional.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.ui.AppNavigation
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@Composable
fun BienestarEmocionalApp()
{
    //nav controller init
    val navController = rememberNavController()

    val context = LocalContext.current

    val healthConnectClient = HealthConnectClient.getOrCreate(context)
    val healthConnectManager = HealthConnectManager(healthConnectClient,context)

    //read ui settings
    val appSettings = AppSettings(context)

    val darkTheme : ThemeMode
    val dynamicColors : Boolean

    runBlocking(Dispatchers.IO)
    {
        darkTheme = appSettings.getTheme().first()
        dynamicColors = appSettings.getDynamicColors().first()
    }

    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        AppNavigation(
            navController = navController,
            appSettings = appSettings,
            healthConnectManager = healthConnectManager,
            healthConnectClient = healthConnectClient,
            darkTheme = darkTheme.themeIsDark()
        )
    }
}