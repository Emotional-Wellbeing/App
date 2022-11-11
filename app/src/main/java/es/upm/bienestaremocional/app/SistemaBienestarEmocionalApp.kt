package es.upm.bienestaremocional.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.app.ui.AppNavigation
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp()
{
    //nav controller init
    val navController = rememberNavController()

    val healthConnectClient = HealthConnectClient.getOrCreate(LocalContext.current)
    val healthConnectManager = HealthConnectManager(healthConnectClient,LocalContext.current)

    BienestarEmocionalTheme()
    {
        AppNavigation(
                healthConnectManager = healthConnectManager,
                healthConnectClient = healthConnectClient,
                navController = navController
        )
    }
}