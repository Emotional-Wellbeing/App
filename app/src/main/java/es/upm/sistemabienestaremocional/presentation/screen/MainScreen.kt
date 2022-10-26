package es.upm.sistemabienestaremocional.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import es.upm.sistemabienestaremocional.data.HealthConnectAvailability
import es.upm.sistemabienestaremocional.navigation.Screen

@Composable
fun MainScreen(navController: NavController, healthConnectAvailability: HealthConnectAvailability)
{
    when (healthConnectAvailability)
    {
        HealthConnectAvailability.INSTALLED -> navController.navigate(Screen.HomeScreen.route)
        HealthConnectAvailability.NOT_INSTALLED -> navController.navigate(Screen.ErrorScreen.route)
        HealthConnectAvailability.NOT_SUPPORTED -> navController.navigate(Screen.ErrorScreen.route)
    }


}