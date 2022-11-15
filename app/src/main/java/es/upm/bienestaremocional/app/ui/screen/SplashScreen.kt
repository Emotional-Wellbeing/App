package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import es.upm.bienestaremocional.core.ui.navigation.Screen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.delay

/**
 * @Todo close app when we arrive this screen from other
 */

@Composable
fun Splash()
{
    Column(
        modifier = Modifier.fillMaxSize(),
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
    healthConnectAvailability: HealthConnectAvailability,
    navController: NavHostController)
{
    //init block. Delay simulate it
    LaunchedEffect(key1 = true)
    {
        delay(1000)

        navController.popBackStack() //prevents a return to splash screen
        when (healthConnectAvailability)
        {
            HealthConnectAvailability.INSTALLED -> navController.navigate(Screen.HomeScreen.route)
            HealthConnectAvailability.NOT_INSTALLED -> navController.navigate(Screen.ErrorScreen.route)
            HealthConnectAvailability.NOT_SUPPORTED -> navController.navigate(Screen.ErrorScreen.route)
        }
    }
    //splash screen
    Splash()
}

@Preview(showBackground = true)
@Composable
fun SplashPreview()
{
    BienestarEmocionalTheme {
        Splash()
    }
}