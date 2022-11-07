package es.upm.bienestaremocional.app.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upm.bienestaremocional.core.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.app.sleep.ui.SleepScreen
import es.upm.bienestaremocional.app.sleep.ui.SleepSessionViewModel
import es.upm.bienestaremocional.app.sleep.ui.SleepSessionViewModelFactory
import es.upm.bienestaremocional.app.ui.screen.*

import es.upm.bienestaremocional.app.showExceptionSnackbar
import es.upm.bienestaremocional.core.ui.navigation.Screen

/**
 * Manages the navigation in the app
 */

@Composable
fun AppNavigation(navController: NavHostController, healthConnectManager: HealthConnectManager)
{
    //to show exceptions
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val availability by healthConnectManager.availability

    NavHost(navController = navController, startDestination = Screen.MainScreen.route)
    {
        composable(route = Screen.MainScreen.route)
        {
            MainScreen(healthConnectAvailability = availability,
                onSucess = { navController.navigate(Screen.HomeScreen.route) },
                onFailure = { navController.navigate(Screen.ErrorScreen.route) })
        }

        composable(route = Screen.ErrorScreen.route)
        {
            ErrorScreen(healthConnectAvailability = availability)
        }

        composable(route = Screen.HomeScreen.route)
        {
            HomeScreen(
                navController = navController,
                onSleepClick = { navController.navigate(Screen.SleepScreen.route) })
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
            //get viewmodel to access sleep data
            val viewModel: SleepSessionViewModel = viewModel(
                factory = SleepSessionViewModelFactory(healthConnectManager = healthConnectManager)
            )

            val sessionsList by viewModel.sessionsList
            val permissions = viewModel.permissions
            val onPermissionsResult = {viewModel.initialLoad()}
            val permissionsLauncher =
                rememberLauncherForActivityResult(viewModel.permissionsLauncher)
                { onPermissionsResult() }

            SleepScreen(
                permissions = permissions,
                uiState = viewModel.uiState,
                sessionsList = sessionsList,
                onPermissionsResult = onPermissionsResult,
                onRequestPermissions = { values -> permissionsLauncher.launch(values)},
                onError = {exception -> showExceptionSnackbar(scope, snackbarHostState, exception) }
            )
        }
    }
}