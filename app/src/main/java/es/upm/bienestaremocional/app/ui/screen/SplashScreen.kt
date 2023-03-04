package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.state.SplashState
import es.upm.bienestaremocional.app.ui.viewmodel.SplashViewModel
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.delay

/**
 * Splash screen of the app. Show the app icon and prompt runtime permissions like notifications
 * or exact alarms
 */
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    splashViewModel: SplashViewModel = hiltViewModel()
)
{
    //splash screen
    Splash(splashViewModel.getDarkTheme())

    when(splashViewModel.state.value)
    {
        SplashState.NotificationsDialog -> { splashViewModel.NotificationsDialogAction() }
        SplashState.ExactDialog -> {

            AlertDialog(onDismissRequest = {splashViewModel.exactDialogAction(false)},
                confirmButton = {
                    TextButton(onClick = { splashViewModel.exactDialogAction(true) })
                    {
                        Text(stringResource(R.string.ok))
                    }
                },
                dismissButton = {
                    TextButton(onClick = {splashViewModel.exactDialogAction(false)}) {
                        Text(stringResource(R.string.skip))
                    }
                },
                title = {
                    Text(stringResource(R.string.permission_for_exact_notifications))
                },
                text = {
                    Text(stringResource(R.string.permission_for_exact_notifications_alert_body))
                })
        }
        SplashState.NoDialog ->
        {
            //init block. Delay simulate loading
            LaunchedEffect(true)
            {
                delay(1000)

                navigator.popBackStack()

                navigator.navigate(splashViewModel.noDialogAction())
            }
        }
    }
}

/**
 * Draw Splash feature (icon + background)
 */
@Composable
private fun Splash(darkTheme: Boolean)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors =
                    if (darkTheme)
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimary
                        )
                    else
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimaryContainer
                        )
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
