package es.upm.sistemabienestaremocional.presentation

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import es.upm.sistemabienestaremocional.data.HealthConnectManager
import es.upm.sistemabienestaremocional.presentation.screen.WelcomeScreen
import es.upm.sistemabienestaremocional.presentation.theme.SistemaBienestarEmocionalTheme


@Composable
fun SistemaBienestarEmocionalApp(healthConnectManager: HealthConnectManager)
{
    //availability of Health Connect
    val availability by healthConnectManager.availability

    SistemaBienestarEmocionalTheme()
    {
        Surface{
            WelcomeScreen(
                healthConnectManager = healthConnectManager,
                healthConnectAvailability = availability,
                onResumeAvailabilityCheck = { healthConnectManager.checkAvailability() })
        }
    }
}