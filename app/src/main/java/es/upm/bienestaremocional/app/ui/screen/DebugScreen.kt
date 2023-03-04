package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.viewmodel.DebugViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Debug screen with certain buttons to debug application. Only available on dev versions
 */
@Destination
@Composable
fun DebugScreen(navigator: DestinationsNavigator, viewModel: DebugViewModel = hiltViewModel())
{
    DebugScreen(navigator = navigator,
        onNotification = { viewModel.onNotification() })
}

@Composable
private fun DebugScreen(navigator: DestinationsNavigator,
                        onNotification: () -> Unit)
{
    AppBasicScreen(navigator = navigator,
            entrySelected = BottomBarDestination.DebugScreen,
            label = BottomBarDestination.DebugScreen.label,
        )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            SettingsMenuLink(
                title = { Text(text = "Notificacion y cuestionario",
                    color = MaterialTheme.colorScheme.secondary) },
                onClick = onNotification,
            )
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun DebugScreenPreview()
{
    BienestarEmocionalTheme{
        DebugScreen(navigator = EmptyDestinationsNavigator, onNotification = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun DebugScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        DebugScreen(navigator = EmptyDestinationsNavigator, onNotification = {})
    }
}