package es.upm.bienestaremocional.ui.screens.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.ui.component.NotInstalledMessage
import es.upm.bienestaremocional.ui.component.NotSupportedMessage
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * This screen is shown when we need show an error message.
 */
@Destination
@Composable
fun ErrorScreen(healthConnectAvailability: HealthConnectAvailability) {
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
            when (healthConnectAvailability) {
                HealthConnectAvailability.NOT_INSTALLED -> NotInstalledMessage()
                HealthConnectAvailability.NOT_SUPPORTED -> NotSupportedMessage()
                else -> {} //HealthConnectAvailability.INSTALLED is not used in this screen
            }
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun NotInstalledMessagePreview() {
    val healthConnectAvailability = HealthConnectAvailability.NOT_INSTALLED
    BienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun NotInstalledMessagePreviewDarkTheme() {
    val healthConnectAvailability = HealthConnectAvailability.NOT_INSTALLED
    BienestarEmocionalTheme(darkTheme = true) {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun NotSupportedMessagePreview() {
    val healthConnectAvailability = HealthConnectAvailability.NOT_SUPPORTED
    BienestarEmocionalTheme {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun NotSupportedMessagePreviewDarkTheme() {
    val healthConnectAvailability = HealthConnectAvailability.NOT_SUPPORTED
    BienestarEmocionalTheme(darkTheme = true) {
        ErrorScreen(healthConnectAvailability = healthConnectAvailability)
    }
}
