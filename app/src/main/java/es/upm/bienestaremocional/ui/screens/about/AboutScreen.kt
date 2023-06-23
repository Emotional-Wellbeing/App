package es.upm.bienestaremocional.ui.screens.about

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
import es.upm.bienestaremocional.BuildConfig
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.TextScreen
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Contains info about app and project
 */
@Destination
@Composable
fun AboutScreen(navigator: DestinationsNavigator) {
    AboutScreen(
        navigator = navigator,
        widthSize = computeWindowWidthSize()
    )
}

@Composable
private fun AboutScreen(
    navigator: DestinationsNavigator,
    widthSize: WindowWidthSizeClass,
) {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.version_placeholder, BuildConfig.VERSION_NAME),
            textAlign = TextAlign.Justify,
            style = textStyle
        )
    }

    TextScreen(
        navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.app_name,
        textContent = content,
        widthSize = widthSize
    )
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun AboutScreenPreview() {
    BienestarEmocionalTheme {
        AboutScreen(
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
fun AboutScreenPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        AboutScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
        )
    }
}