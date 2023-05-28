
package es.upm.bienestaremocional.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Welcome text shown when the Healthcore APK is not yet installed on the device, prompting the user
 * to install it.
 */
@Composable
fun NotInstalledMessage() {
    Text(
        text = stringResource(id = R.string.not_installed_welcome_message),
        textAlign = TextAlign.Justify,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun NotInstalledMessagePreview()
{
    BienestarEmocionalTheme {
        Surface {
            NotInstalledMessage()
        }
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun NotInstalledMessagePreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            NotInstalledMessage()
        }
    }
}

