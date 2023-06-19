package es.upm.bienestaremocional.ui.screens.error

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.ui.component.TextScreen
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.MIN_SUPPORTED_SDK

/**
 * This screen is shown when we need show an error message.
 */
@Destination
@Composable
fun ErrorScreen(healthConnectAvailability: HealthConnectAvailability) {
    ErrorScreen(
        healthConnectAvailability = healthConnectAvailability,
        widthSize = computeWindowWidthSize()
    )
}

@Composable
private fun ErrorScreen(
    healthConnectAvailability: HealthConnectAvailability,
    widthSize: WindowWidthSizeClass,
) {

    val textContent: @Composable (TextStyle) -> Unit = { textStyle ->

        val stringToShow = when (healthConnectAvailability) {
            HealthConnectAvailability.NOT_INSTALLED ->
                stringResource(id = R.string.not_installed_welcome_message)

            HealthConnectAvailability.NOT_SUPPORTED ->
                stringResource(id = R.string.not_supported_welcome_message, MIN_SUPPORTED_SDK)

            else -> null
        }

        stringToShow?.let {
            Text(
                text = it,
                textAlign = TextAlign.Justify,
                style = textStyle
            )
        }
    }

    TextScreen(
        textContent = textContent,
        widthSize = widthSize
    )
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun NotInstalledMessagePreview() {
    BienestarEmocionalTheme {
        ErrorScreen(
            healthConnectAvailability = HealthConnectAvailability.NOT_INSTALLED,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun NotInstalledMessagePreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        ErrorScreen(
            healthConnectAvailability = HealthConnectAvailability.NOT_INSTALLED,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun NotSupportedMessagePreview() {
    BienestarEmocionalTheme {
        ErrorScreen(
            healthConnectAvailability = HealthConnectAvailability.NOT_SUPPORTED,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun NotSupportedMessagePreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        ErrorScreen(
            healthConnectAvailability = HealthConnectAvailability.NOT_SUPPORTED,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}
