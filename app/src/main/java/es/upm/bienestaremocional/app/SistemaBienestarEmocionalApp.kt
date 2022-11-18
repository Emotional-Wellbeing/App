package es.upm.bienestaremocional.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.app.ui.AppNavigation
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp()
{
    //nav controller init
    val navController = rememberNavController()

    val context = LocalContext.current

    val healthConnectClient = HealthConnectClient.getOrCreate(context)
    val healthConnectManager = HealthConnectManager(healthConnectClient,context)

    val appSettings = AppSettings(context)

    BienestarEmocionalTheme()
    {
        AppNavigation(
            navController = navController,
            appSettings = appSettings,
            healthConnectManager = healthConnectManager,
            healthConnectClient = healthConnectClient
        )
    }
}