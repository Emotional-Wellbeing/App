
package es.upm.bienestaremocional.app.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.extraction.healthconnect.data.MIN_SUPPORTED_SDK
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


/**
 * Welcome text shown when the app first starts, where the device is not running a sufficient
 * Android version for Health Connect to be used.
 */
@Composable
fun NotSupportedMessage() {
    Text(
        text = stringResource(id = R.string.not_supported_welcome_message, MIN_SUPPORTED_SDK),
        textAlign = TextAlign.Justify,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview
@Composable
fun NotSupportedMessagePreview() {
    BienestarEmocionalTheme {
        NotSupportedMessage()
    }
}

@Preview
@Composable
fun NotSupportedMessagePreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        NotSupportedMessage()
    }
}