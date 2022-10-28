
package es.upm.sistemabienestaremocional.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.healthconnect.MIN_SUPPORTED_SDK
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

/**
 * Welcome text shown when the app first starts, where the device is not running a sufficient
 * Android version for Health Connect to be used.
 */
@Composable
fun NotSupportedMessage() {
    Text(
        text = stringResource(id = R.string.not_supported_welcome_message, MIN_SUPPORTED_SDK),
        textAlign = TextAlign.Justify
    )
}

@Preview
@Composable
fun NotSupportedMessagePreview() {
    SistemaBienestarEmocionalTheme {
        NotSupportedMessage()
    }
}
