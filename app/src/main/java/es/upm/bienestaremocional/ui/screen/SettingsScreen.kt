package es.upm.bienestaremocional.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.SwitchWithLabel
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Renders settings menu
 */

@Composable
fun SettingsScreen(navController: NavController)
{
    AppBasicScreen(navController = navController, entrySelected = LocalMenuEntry.SettingsScreen)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            SwitchWithLabel(label = "Hello world")
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme {
        SettingsScreen(navController)
    }
}