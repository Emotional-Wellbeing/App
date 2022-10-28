package es.upm.sistemabienestaremocional.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.sistemabienestaremocional.component.NotInstalledMessage
import es.upm.sistemabienestaremocional.component.NotSupportedMessage
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectAvailability
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

/**
 * This screen is shown when we need show an error message.
 */
@Composable
fun ErrorScreen(healthConnectAvailability: HealthConnectAvailability)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        when (healthConnectAvailability) {
            HealthConnectAvailability.NOT_INSTALLED -> NotInstalledMessage()
            HealthConnectAvailability.NOT_SUPPORTED -> NotSupportedMessage()
            else -> {} //HealthConnectAvailability.INSTALLED is not used in this screen
        }
    }
}

@Preview
@Composable
fun InstalledMessagePreview() {
    SistemaBienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = HealthConnectAvailability.INSTALLED)
    }
}

@Preview
@Composable
fun NotInstalledMessagePreview() {
    SistemaBienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = HealthConnectAvailability.NOT_INSTALLED)
    }
}

@Preview
@Composable
fun NotSupportedMessagePreview() {
    SistemaBienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = HealthConnectAvailability.NOT_SUPPORTED)
    }
}
