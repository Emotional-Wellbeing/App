package es.upm.bienestaremocional.app.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.health.connect.client.HealthConnectClient
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upm.bienestaremocional.app.data.heartrate.HealthConnectHeartrate
import es.upm.bienestaremocional.app.showExceptionSnackbar
import es.upm.bienestaremocional.app.ui.screen.*
import es.upm.bienestaremocional.app.ui.sleep.SleepScreen
import es.upm.bienestaremocional.app.data.sleep.HealthConnectSleep
import es.upm.bienestaremocional.app.ui.heartrate.HeartrateScreen
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.ui.navigation.Screen

/**
 * Manages the navigation in the app
 */

@Composable
fun AppNavigation(navController: NavHostController,
                  healthConnectClient: HealthConnectClient,
                  healthConnectManager: HealthConnectManager)
{
    //to show exceptions
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val healthConnectSleep = HealthConnectSleep(healthConnectClient, healthConnectManager)
    val healthConnectHeartrate = HealthConnectHeartrate(healthConnectClient, healthConnectManager)
    val availability by healthConnectManager.availability

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route)
    {
        composable(route = Screen.SplashScreen.route)
        {
            SplashScreen(healthConnectAvailability = availability, navController = navController)
        }

        composable(route = Screen.ErrorScreen.route)
        {
            ErrorScreen(healthConnectAvailability = availability)
        }

        composable(route = Screen.HomeScreen.route)
        {
            HomeScreen(
                navController = navController,
                onSleepClick = { navController.navigate(Screen.SleepScreen.route) },
                onHeartrateClick = { navController.navigate(Screen.HeartrateScreen.route) })
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

        composable(route = Screen.SleepScreen.route)
        {
            SleepScreen(healthConnectSleep) {
                    exception -> showExceptionSnackbar(scope, snackbarHostState, exception)
            }
        }

        composable(route = Screen.HeartrateScreen.route)
        {
            HeartrateScreen(healthConnectHeartrate){
                    exception -> showExceptionSnackbar(scope, snackbarHostState, exception)
            }
        }

    }
}