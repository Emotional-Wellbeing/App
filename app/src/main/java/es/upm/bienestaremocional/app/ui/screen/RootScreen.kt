package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.ui.screen.destinations.SplashScreenDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun RootScreen(navigator: DestinationsNavigator)
{
    val darkTheme : Boolean = MainApplication.appSettings.getThemeValue().themeIsDark()

    navigator.navigate(SplashScreenDestination(darkTheme = darkTheme))
}