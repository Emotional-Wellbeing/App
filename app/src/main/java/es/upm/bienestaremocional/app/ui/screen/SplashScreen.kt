package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.ui.navigation.Screen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

/**
 * @Todo close app when we arrive this screen from other
 */

@Composable
fun Splash(darkTheme: Boolean)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors =
                    if (darkTheme)
                        listOf(MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimary)
                    else
                        listOf(MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimaryContainer)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Logo aplicación",
            modifier = Modifier.fillMaxSize(0.5f))
    }
}

@Composable
fun SplashScreen(
    appSettings: AppSettingsInterface,
    healthConnectAvailability: HealthConnectAvailability,
    navController: NavHostController,
    darkTheme: Boolean
)
{

    //splash screen
    Splash(darkTheme)

    //init block. Delay simulate it
    LaunchedEffect(key1 = true)
    {
        delay(1000)

        navController.popBackStack() //prevents a return to splash screen

        //read if we should present onboarding
        val showOnboarding = appSettings.getShowOnboarding().first()


        when (healthConnectAvailability)
        {
            HealthConnectAvailability.INSTALLED -> if (showOnboarding)
                navController.navigate(Screen.OnboardingScreen.route)
            else
                navController.navigate(Screen.HomeScreen.route)

            HealthConnectAvailability.NOT_INSTALLED -> navController.navigate(Screen.ErrorScreen.route)
            HealthConnectAvailability.NOT_SUPPORTED -> navController.navigate(Screen.ErrorScreen.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview()
{
    BienestarEmocionalTheme {
        Splash(darkTheme = false)
    }
}

@Preview(showBackground = true)
@Composable
fun SplashDarkThemePreview()
{
    BienestarEmocionalTheme {
        Splash(darkTheme = true)
    }
}
