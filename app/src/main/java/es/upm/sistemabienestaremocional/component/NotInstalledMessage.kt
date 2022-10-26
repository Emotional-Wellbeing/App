
package es.upm.sistemabienestaremocional.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.presentation.theme.SistemaBienestarEmocionalTheme

/**
 * Welcome text shown when the Healthcore APK is not yet installed on the device, prompting the user
 * to install it.
 */
@Composable
fun NotInstalledMessage() {
    Text(
        text = stringResource(id = R.string.not_installed_welcome_message),
        textAlign = TextAlign.Justify
    )
}

@Preview
@Composable
fun NotInstalledMessagePreview()
{
    SistemaBienestarEmocionalTheme {
        NotInstalledMessage()
    }
}
