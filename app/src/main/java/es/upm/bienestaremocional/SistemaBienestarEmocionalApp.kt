package es.upm.bienestaremocional

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.data.healthconnect.HealthConnectManager
import es.upm.bienestaremocional.ui.navigation.AppNavigation
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme


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