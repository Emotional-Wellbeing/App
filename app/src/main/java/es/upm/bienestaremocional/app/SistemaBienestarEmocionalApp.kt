package es.upm.bienestaremocional.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.core.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.app.ui.AppNavigation
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp(healthConnectManager: HealthConnectManager)
{
    //nav controller init
    val navController = rememberNavController()

    BienestarEmocionalTheme()
    {
        AppNavigation(
                healthConnectManager = healthConnectManager,
                navController = navController
        )
    }
}