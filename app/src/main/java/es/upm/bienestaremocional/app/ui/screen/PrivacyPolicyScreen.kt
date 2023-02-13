
package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.navigation.MenuEntry
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Shows the privacy policy.
 */
@Destination
@Composable
fun PrivacyPolicyScreen(navigator: DestinationsNavigator)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = MenuEntry.SettingsScreen,
        label = R.string.privacy_policy_screen_label)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            BasicCard{
                Text(stringResource(R.string.privacy_policy_description))
            }
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PrivacyPolicyScreenPreview()
{

    BienestarEmocionalTheme {
        PrivacyPolicyScreen(EmptyDestinationsNavigator)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun PrivacyPolicyScreenPreviewDarkTheme()
{

    BienestarEmocionalTheme(darkTheme = true) {
        PrivacyPolicyScreen(EmptyDestinationsNavigator)
    }
}