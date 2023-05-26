package es.upm.bienestaremocional.app

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.ui.screens.NavGraphs
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


@Composable
fun BienestarEmocionalApp(darkTheme: ThemeMode,
                          dynamicColors : Boolean
)
{
    BienestarEmocionalTheme(darkTheme = darkTheme.themeIsDark(), dynamicColors = dynamicColors)
    {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}