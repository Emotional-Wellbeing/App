package es.upm.sistemabienestaremocional.screen

import androidx.compose.runtime.Composable
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectAvailability

/**
 * @Todo close app when we arrive this screen from other
 */

@Composable
fun MainScreen(
    healthConnectAvailability: HealthConnectAvailability,
    onSucess: () -> Unit,
    onFailure : () -> Unit)
{
    when (healthConnectAvailability)
    {
        HealthConnectAvailability.INSTALLED -> onSucess()
        HealthConnectAvailability.NOT_INSTALLED -> onFailure()
        HealthConnectAvailability.NOT_SUPPORTED -> onFailure()
    }
}