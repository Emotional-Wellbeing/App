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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.ui.screen.destinations.ErrorScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.HomeScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.OnboardingScreenDestination
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.delay

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

@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    healthConnectAvailability: HealthConnectAvailability,
    showOnboarding : Boolean,
    darkTheme: Boolean
)
{
    //splash screen
    Splash(darkTheme)

    //init block. Delay simulate loading
    LaunchedEffect(true)
    {
        delay(1000)

        navigator.popBackStack() //prevents a return to splash screen


        //redirect to certain screen
        when (healthConnectAvailability)
        {
            HealthConnectAvailability.INSTALLED ->

                if (showOnboarding)
                    navigator.navigate(OnboardingScreenDestination(MainApplication.windowSize!!))
                else
                    navigator.navigate(HomeScreenDestination)
            else -> navigator.navigate(ErrorScreenDestination(healthConnectAvailability))
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
fun SplashPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Splash(darkTheme = true)
    }
}
