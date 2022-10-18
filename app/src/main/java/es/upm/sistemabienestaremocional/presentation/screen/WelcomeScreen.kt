package es.upm.sistemabienestaremocional.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.data.HealthConnectAvailability
import es.upm.sistemabienestaremocional.data.HealthConnectManager
import es.upm.sistemabienestaremocional.presentation.theme.SistemaBienestarEmocionalTheme

/**
 * Welcome screen shown when the app is first launched.
 */
@Composable
fun WelcomeScreen(
    healthConnectManager: HealthConnectManager,
    healthConnectAvailability: HealthConnectAvailability,
    onResumeAvailabilityCheck: () -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val currentOnAvailabilityCheck by rememberUpdatedState(onResumeAvailabilityCheck)
    var openSleepScreen by remember {mutableStateOf(false)}

    // Add a listener to re-check whether Health Connect has been installed each time the Welcome
    // screen is resumed: This ensures that if the user has been redirected to the Play store and
    // followed the onboarding flow, then when the app is resumed, instead of showing the message
    // to ask the user to install Health Connect, the app recognises that Health Connect is now
    // available and shows the appropriate welcome.
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                currentOnAvailabilityCheck()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        Text(text = stringResource(R.string.welcome_text))
        
        Spacer(modifier = Modifier.height(32.dp))
        
        when (healthConnectAvailability) {
            HealthConnectAvailability.INSTALLED -> InstalledMessage()
            HealthConnectAvailability.NOT_INSTALLED -> NotInstalledMessage()
            HealthConnectAvailability.NOT_SUPPORTED -> NotSupportedMessage()
        }

        if(healthConnectAvailability == HealthConnectAvailability.INSTALLED)
        {
            Button(
                onClick = { openSleepScreen = true })
            {
                Text("Sueño")
            }
        }
        if (openSleepScreen) OpenSleepScreen(healthConnectManager)
    }
}

@Composable
fun OpenSleepScreen(healthConnectManager: HealthConnectManager)
{
    val viewModel: SleepSessionViewModel = viewModel(
            factory = SleepSessionViewModelFactory(healthConnectManager = healthConnectManager))
    val permissionsGranted by viewModel.permissionsGranted
    val sessionsList by viewModel.sessionsList
    val permissions = viewModel.permissions

    val onPermissionsResult = {viewModel.initialLoad()}

    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionsLauncher) {
            onPermissionsResult()}

    SleepSessionScreen(
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

/**
 * Welcome text shown when the app first starts, where the Healthcore APK is already installed.
 */
@Composable
fun InstalledMessage() {
    Text(
        text = "App instalada",
        textAlign = TextAlign.Justify
    )
}

@Composable
fun NotInstalledMessage() {
    Text(
        text = "Falta por instalar Health Connect",
        textAlign = TextAlign.Justify
    )
}

/**
 * Welcome text shown when the app first starts, where the device is not running a sufficient
 * Android version for Health Connect to be used.
 */
@Composable
fun NotSupportedMessage() {
    Text(
        text = "Necesitas mínimo Android 8.1 (SDK 27) para instalar esta APP",
        textAlign = TextAlign.Justify
    )
}

@Preview
@Composable
fun InstalledMessagePreview() {
    SistemaBienestarEmocionalTheme {
        WelcomeScreen(
            healthConnectManager = HealthConnectManager(LocalContext.current),
            healthConnectAvailability = HealthConnectAvailability.INSTALLED,
            onResumeAvailabilityCheck = {}
        )
    }
}

@Preview
@Composable
fun NotInstalledMessagePreview() {
    SistemaBienestarEmocionalTheme {
        WelcomeScreen(
            healthConnectManager = HealthConnectManager(LocalContext.current),
            healthConnectAvailability = HealthConnectAvailability.NOT_INSTALLED,
            onResumeAvailabilityCheck = {}
        )
    }
}

@Preview
@Composable
fun NotSupportedMessagePreview() {
    SistemaBienestarEmocionalTheme {
        WelcomeScreen(
            healthConnectManager = HealthConnectManager(LocalContext.current),
            healthConnectAvailability = HealthConnectAvailability.NOT_SUPPORTED,
            onResumeAvailabilityCheck = {}
        )
    }
}
