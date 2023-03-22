package es.upm.bienestaremocional.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.component.BackHandlerMinimizeApp
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Home Screen has the latest news about user and is displayed when splash ends
 */
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModel: HomeViewModel = hiltViewModel())
{
    HomeScreen(navigator = navigator)
}


@Composable
private fun HomeScreen(navigator: DestinationsNavigator)
{
    BackHandlerMinimizeApp(LocalContext.current)

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
        HomeScreen(navigator = EmptyDestinationsNavigator)
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
        HomeScreen(navigator = EmptyDestinationsNavigator)
    }
}