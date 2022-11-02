package es.upm.sistemabienestaremocional

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectManager
import es.upm.sistemabienestaremocional.navigation.AppNavigation
import es.upm.sistemabienestaremocional.ui.theme.SistemaBienestarEmocionalTheme


@Composable
fun SistemaBienestarEmocionalApp(healthConnectManager: HealthConnectManager)
{
    //nav controller init
    val navController = rememberNavController()

    SistemaBienestarEmocionalTheme()
    {
        AppNavigation(
                healthConnectManager = healthConnectManager,
                navController = navController
        )
    }
}