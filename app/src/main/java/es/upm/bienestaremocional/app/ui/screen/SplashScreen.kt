package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.ui.navigation.Screen
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
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
            modifier = Modifier.fillMaxSize(0.66f))
    }
}

@Composable
fun SplashScreen(
    appSettings: AppSettingsInterface,
    healthConnectAvailability: MutableState<HealthConnectAvailability>,
    navController: NavHostController,
    darkTheme: Boolean
)
{
    //splash screen
    Splash(darkTheme)

    //init block. Delay simulate loading
    LaunchedEffect(true)
    {
        delay(1000)

        navController.popBackStack() //prevents a return to splash screen

        //read if we should present onboarding
        val showOnboarding = appSettings.getShowOnboarding().first()

        //redirect to certain screen
        when (healthConnectAvailability.value)
        {
            HealthConnectAvailability.INSTALLED ->
                if (showOnboarding)
                    navController.navigate(Screen.OnboardingScreen.route)
                else
                    navController.navigate(Screen.HomeScreen.route)

            else -> navController.navigate(Screen.ErrorScreen.route)
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun SplashPreview()
{
    BienestarEmocionalTheme {
        Splash(darkTheme = false)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun SplashPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Splash(darkTheme = true)
    }
}
