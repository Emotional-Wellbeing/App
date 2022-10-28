package es.upm.sistemabienestaremocional

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectManager
import es.upm.sistemabienestaremocional.navigation.SBENavigation
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SistemaBienestarEmocionalApp(healthConnectManager: HealthConnectManager)
{
    //nav controller init
    val navController = rememberNavController()

    SistemaBienestarEmocionalTheme()
    {
        Scaffold {
            SBENavigation(
                healthConnectManager = healthConnectManager,
                navController = navController
            )
        }

        //MainScreen(navController = navController, healthConnectAvailability = availability)
    }
}