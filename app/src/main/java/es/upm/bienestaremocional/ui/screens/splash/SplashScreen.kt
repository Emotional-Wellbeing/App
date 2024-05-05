package es.upm.bienestaremocional.ui.screens.splash

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.delay

/**
 * Splash screen of the app. Show the app icon and prompt runtime permissions like notifications
 */
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    //splash screen
    Splash(
        darkTheme = splashViewModel.getDarkTheme()
    )

    val uiState by splashViewModel.state.collectAsStateWithLifecycle()

    when (uiState) {
        SplashState.Init -> {
            splashViewModel.onInit()
        }

        SplashState.NotificationsDialog -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                splashViewModel.OnNotificationsDialog()
            }
        }

        SplashState.Loading -> {
            LaunchedEffect(true)
            {
                splashViewModel.onLoading()
                delay(1000)
            }
        }

        SplashState.Redirect -> {
            navigator.navigate(splashViewModel.onRedirect())
        }
    }
}

/**
 * Draw Splash feature (icon + background)
 */
@Composable
private fun Splash(darkTheme: Boolean) {
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
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Logo aplicación",
            modifier = Modifier.fillMaxSize(0.66f)
        )
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun SplashPreview() {
    BienestarEmocionalTheme {
        Splash(darkTheme = false)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun SplashPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        Splash(darkTheme = true)
    }
}
