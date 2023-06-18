package es.upm.bienestaremocional

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.data.settings.ThemeMode
import es.upm.bienestaremocional.ui.screens.NavGraphs
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp(
    darkTheme: ThemeMode,
    dynamicColors: Boolean
) {
    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}