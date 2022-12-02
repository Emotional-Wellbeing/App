package es.upm.bienestaremocional.app.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.showExceptionSnackbar
import es.upm.bienestaremocional.app.ui.healthconnect.DebugScreen
import es.upm.bienestaremocional.app.ui.screen.*
import es.upm.bienestaremocional.app.ui.settings.SettingsScreen
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.ui.navigation.Screen
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import kotlinx.coroutines.launch

/**
 * Manages the navigation in the app
 */

@Composable
fun AppNavigation(navController: NavHostController,
                  appSettings: AppSettingsInterface,
                  windowSize: WindowSize,
                  healthConnectAvailability: MutableState<HealthConnectAvailability>,
                  darkTheme : Boolean
)
{
    //to show exceptions
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route)
    {
        composable(route = Screen.SplashScreen.route)
        {
            SplashScreen(
                appSettings = appSettings,
                healthConnectAvailability = healthConnectAvailability,
                navController = navController,
                darkTheme = darkTheme
            )
        }

        composable(route = Screen.ErrorScreen.route)
        {
            ErrorScreen(healthConnectAvailability = healthConnectAvailability)
        }

        composable(route = Screen.OnboardingScreen.route)
        {
            OnboardingScreen(windowSize = windowSize)
            {
                scope.launch {
                    appSettings.saveShowOnboarding(false)
                }
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            }
        }

        composable(route = Screen.HomeScreen.route)
        {
            HomeScreen(
                navController = navController,
                onDebugClick = { navController.navigate(Screen.DebugScreen.route) })
        }

        composable(route = Screen.HistoryScreen.route)
        {
            HistoryScreen(navController)
        }

        composable(route = Screen.EvolutionScreen.route)
        {
            EvolutionScreen(navController)
        }

        composable(route = Screen.TrendsScreen.route)
        {
            TrendsScreen(navController)
        }

        composable(route = Screen.SettingsScreen.route)
        {
            SettingsScreen(navController)
        }

        composable(route = Screen.PrivacyPolicyScreen.route)
        {
            PrivacyPolicyScreen(navController)
        }

        composable(route = Screen.AboutScreen.route)
        {
            AboutScreen(navController)
        }

        composable(route = Screen.CreditsScreen.route)
        {
            CreditsScreen(navController = navController, windowSize = windowSize)
        }

        composable(route = Screen.DebugScreen.route)
        {
            DebugScreen(
                windowSize = windowSize,
                onError = { exception ->
                    showExceptionSnackbar(
                        scope,
                        snackbarHostState,
                        exception
                    )
                })
        }
    }
}