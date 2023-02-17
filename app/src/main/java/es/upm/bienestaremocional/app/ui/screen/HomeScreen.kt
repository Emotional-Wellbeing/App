package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.viewmodel.HomeViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Home Screen is point of entry screen
 */

@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModel: HomeViewModel = hiltViewModel())
{
    HomeScreen(navigator = navigator,
        onNotification = { viewModel.onNotification() },
        onQuestionnaire = { viewModel.onQuestionnaire(navigator) })
}

@Composable
private fun HomeScreen(
    navigator: DestinationsNavigator,
    onNotification: () -> Unit,
    onQuestionnaire: () -> Unit)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HomeScreen,
        label = R.string.app_name)
    {
        //https://developer.android.com/jetpack/compose/gestures for verticalScroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            BasicCard{
                Text("Message placeholder")
            }

            BasicCard{
                Text("Today stats placeholder")
            }

            BasicCard{
                Text("PHQ-9 placeholder")
            }

            BasicCard{
                Text("Feedback placeholder")
            }

            BasicCard{
                Text("Last week stats placeholder")
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text("Debug buttons, not present in final version", textAlign = TextAlign.Justify)
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Button(onClick = onNotification)
                    {
                        Text(text = "Notificacion")
                    }
                    Button(onClick = onQuestionnaire)
                    {
                        Text(text = "Cuestionario")
                    }
                }
            }

        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HomeScreenPreview()
{
    BienestarEmocionalTheme{
        HomeScreen(navigator = EmptyDestinationsNavigator,
            onNotification = {},
            onQuestionnaire = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navigator = EmptyDestinationsNavigator,
            onNotification = {},
            onQuestionnaire = {})
    }
}