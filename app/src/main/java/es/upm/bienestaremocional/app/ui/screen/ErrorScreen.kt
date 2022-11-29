package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.app.ui.component.NotInstalledMessage
import es.upm.bienestaremocional.app.ui.component.NotSupportedMessage
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * This screen is shown when we need show an error message.
 */
@Composable
fun ErrorScreen(healthConnectAvailability: MutableState<HealthConnectAvailability>)
{
    Surface(color = MaterialTheme.colorScheme.background)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            when (healthConnectAvailability.value) {
                HealthConnectAvailability.NOT_INSTALLED -> NotInstalledMessage()
                HealthConnectAvailability.NOT_SUPPORTED -> NotSupportedMessage()
                else -> {} //HealthConnectAvailability.INSTALLED is not used in this screen
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun NotInstalledMessagePreview() {
    val healthConnectAvailability = remember {mutableStateOf(HealthConnectAvailability.NOT_INSTALLED)}
    BienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}

@Preview(showBackground = true)
@Composable
fun NotInstalledMessagePreviewDarkTheme() {
    val healthConnectAvailability = remember {mutableStateOf(HealthConnectAvailability.NOT_INSTALLED)}
    BienestarEmocionalTheme(darkTheme = true) {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}

@Preview(showBackground = true)
@Composable
fun NotSupportedMessagePreview() {
    val healthConnectAvailability = remember {mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)}
    BienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}

@Preview(showBackground = true)
@Composable
fun NotSupportedMessagePreviewDarkTheme() {
    val healthConnectAvailability = remember {mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)}
    BienestarEmocionalTheme(darkTheme = true) {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}
