package es.upm.bienestaremocional.app.ui.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.navigation.MenuEntry
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Home Screen is point of entry screen
 */
@Composable
fun HomeScreen(navController: NavController)
{
    AppBasicScreen(navController = navController,
        entrySelected = MenuEntry.HomeScreen,
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
    //nav controller init
    val navController = rememberNavController()

    BienestarEmocionalTheme{
        HomeScreen(navController = navController)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HomeScreenPreviewDarkTheme()
{
    //nav controller init
    val navController = rememberNavController()

    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(navController = navController)
    }
}