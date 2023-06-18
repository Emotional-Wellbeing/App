package es.upm.bienestaremocional.ui.screens.privacy

import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.TextScreen
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Shows the privacy policy.
 */
@Destination
@Composable
fun PrivacyPolicyScreen(navigator: DestinationsNavigator)
{
    PrivacyPolicyScreen(
        navigator = navigator,
        widthSize = computeWindowWidthSize()
    )
}

@Composable
private fun PrivacyPolicyScreen(
    navigator: DestinationsNavigator,
    widthSize: WindowWidthSizeClass,
)
{
    val content : @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.privacy_policy_description),
            textAlign = TextAlign.Justify,
            style = textStyle
        )
    }

    TextScreen(
        navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.privacy_policy_screen_label,
        textContent = content,
        widthSize = widthSize
    )
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun PrivacyPolicyScreenPreview()
{
    BienestarEmocionalTheme {
        PrivacyPolicyScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
        )
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
        PrivacyPolicyScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
        )
    }
}