package es.upm.sistemabienestaremocional.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectManager
import es.upm.sistemabienestaremocional.screen.MainScreen
import es.upm.sistemabienestaremocional.screen.ErrorScreen
import es.upm.sistemabienestaremocional.screen.HomeScreen
import es.upm.sistemabienestaremocional.screen.PrivacyPolicyScreen
import es.upm.sistemabienestaremocional.sleep.SleepScreen
import es.upm.sistemabienestaremocional.sleep.SleepSessionViewModel
import es.upm.sistemabienestaremocional.sleep.SleepSessionViewModelFactory

/**
 * Manages the navigation in the app
 */

@Composable
fun SBENavigation(navController: NavHostController, healthConnectManager: HealthConnectManager)
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
                onSleepClick = { navController.navigate(Screen.SleepScreen.route) },
                onPrivacyClick = {navController.navigate(Screen.PrivacyPolicyScreen.route)})
        }
        composable(route = Screen.PrivacyPolicyScreen.route)
        {
            PrivacyPolicyScreen(onBackClick = {navController.navigateUp()})
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