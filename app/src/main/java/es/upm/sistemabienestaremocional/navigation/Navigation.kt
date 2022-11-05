package es.upm.sistemabienestaremocional.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectManager
import es.upm.sistemabienestaremocional.sleep.SleepScreen
import es.upm.sistemabienestaremocional.sleep.SleepSessionViewModel
import es.upm.sistemabienestaremocional.sleep.SleepSessionViewModelFactory
import es.upm.sistemabienestaremocional.ui.screen.*

/**
 * Manages the navigation in the app
 */

@Composable
fun AppNavigation(navController: NavHostController, healthConnectManager: HealthConnectManager)
{
    NavHost(navController = navController, startDestination = Screen.MainScreen.route)
    {
        val availability by healthConnectManager.availability

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
            val viewModel: SleepSessionViewModel = viewModel(
                factory = SleepSessionViewModelFactory(
                    healthConnectManager = healthConnectManager
                )
            )
            val permissionsGranted by viewModel.permissionsGranted
            val sessionsList by viewModel.sessionsList
            val permissions = viewModel.permissions
            val onPermissionsResult = {viewModel.initialLoad()}
            val permissionsLauncher =
                rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
                    onPermissionsResult()}
            SleepScreen(
                permissionsGranted = permissionsGranted,
                permissions = permissions,
                sessionsList = sessionsList,
                onPermissionsResult = {
                    viewModel.initialLoad()
                },
                onPermissionsLaunch = { values ->
                    permissionsLauncher.launch(values)}
            )
        }
    }
}