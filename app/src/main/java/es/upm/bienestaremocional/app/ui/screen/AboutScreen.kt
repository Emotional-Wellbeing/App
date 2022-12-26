package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
 * Contains info about app and project
 */
@Composable
fun AboutScreen(navController: NavController)
{
    AppBasicScreen(navController = navController,
        entrySelected = MenuEntry.SettingsScreen,
        label = R.string.about_screen_label)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            BasicCard {
                Text("About us placeholder")
            }
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun AboutScreenPreview()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme {
        AboutScreen(navController)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun AboutScreenPreviewDarkTheme()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme(darkTheme = true) {
        AboutScreen(navController)
    }
}