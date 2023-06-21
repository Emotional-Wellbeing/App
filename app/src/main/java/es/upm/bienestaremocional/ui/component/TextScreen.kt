package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Define the base detail screen used in the app
 */
@Composable
fun TextScreen(
    navigator: DestinationsNavigator,
    entrySelected: BottomBarDestination?,
    label: Int,
    textContent: @Composable (TextStyle) -> Unit,
    widthSize: WindowWidthSizeClass,
) {
    AppBasicScreen(
        navigator = navigator,
        entrySelected = entrySelected,
        label = label,
    )
    {
        TextScreen(
            textContent = textContent,
            buttonContent = { ButtonContent(navigator = navigator) },
            widthSize = widthSize,
        )
    }
}

@Composable
fun TextScreen(
    navigator: DestinationsNavigator,
    textContent: @Composable (TextStyle) -> Unit,
    widthSize: WindowWidthSizeClass,
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextScreen(
            textContent = textContent,
            buttonContent = { ButtonContent(navigator = navigator) },
            widthSize = widthSize
        )
    }
}

@Composable
fun TextScreen(
    textContent: @Composable (TextStyle) -> Unit,
    widthSize: WindowWidthSizeClass,
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextScreen(
            textContent = textContent,
            buttonContent = null,
            widthSize = widthSize
        )
    }
}

@Composable
private fun ButtonContent(
    navigator: DestinationsNavigator
) {
    FilledTonalButton(onClick = navigator::popBackStack) {
        Text(text = stringResource(id = R.string.go_back))
    }
}

@Composable
private fun TextScreen(
    textContent: @Composable (TextStyle) -> Unit,
    buttonContent: (@Composable () -> Unit)?,
    widthSize: WindowWidthSizeClass,
) {
    val textStyle = when (widthSize) {
        WindowWidthSizeClass.Compact -> MaterialTheme.typography.bodyMedium
        WindowWidthSizeClass.Medium -> MaterialTheme.typography.bodyLarge
        WindowWidthSizeClass.Expanded -> MaterialTheme.typography.bodyLarge
        else -> MaterialTheme.typography.bodyMedium
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        textContent(textStyle)

        buttonContent?.let { it() }
    }
}

@Preview(group = "Light Theme")
@Composable
fun TextScreenFullPreview() {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.about_screen_description),
            style = textStyle
        )
    }
    BienestarEmocionalTheme()
    {
        TextScreen(
            navigator = EmptyDestinationsNavigator,
            entrySelected = null,
            label = R.string.app_name,
            textContent = content,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
fun TextScreenFullPreviewDarkTheme() {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.about_screen_description),
            style = textStyle
        )
    }
    BienestarEmocionalTheme(darkTheme = true)
    {
        TextScreen(
            navigator = EmptyDestinationsNavigator,
            entrySelected = null,
            label = R.string.app_name,
            textContent = content,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Light Theme")
@Composable
fun TextScreenPreview() {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.about_screen_description),
            style = textStyle
        )
    }
    BienestarEmocionalTheme()
    {
        TextScreen(
            navigator = EmptyDestinationsNavigator,
            textContent = content,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
fun TextScreenPreviewDarkTheme() {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.about_screen_description),
            style = textStyle
        )
    }
    BienestarEmocionalTheme(darkTheme = true)
    {
        TextScreen(
            navigator = EmptyDestinationsNavigator,
            textContent = content,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Light Theme")
@Composable
fun TextScreenWithoutButtonPreview() {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.about_screen_description),
            style = textStyle
        )
    }
    BienestarEmocionalTheme()
    {
        TextScreen(
            textContent = content,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
fun TextScreenWithoutButtonPreviewDarkTheme() {
    val content: @Composable (TextStyle) -> Unit = { textStyle ->
        Text(
            text = stringResource(id = R.string.about_screen_description),
            style = textStyle
        )
    }
    BienestarEmocionalTheme(darkTheme = true)
    {
        TextScreen(
            textContent = content,
            widthSize = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(group = "Light Theme")
@Composable
fun ButtonContentPreview() {
    BienestarEmocionalTheme()
    {
        ButtonContent(
            navigator = EmptyDestinationsNavigator
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
fun ButtonContentPreviewDarkTheme() {

    BienestarEmocionalTheme(darkTheme = true)
    {
        ButtonContent(
            navigator = EmptyDestinationsNavigator
        )
    }
}