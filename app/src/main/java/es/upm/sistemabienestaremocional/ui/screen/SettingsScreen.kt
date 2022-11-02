package es.upm.sistemabienestaremocional.ui.screen

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
import es.upm.sistemabienestaremocional.navigation.LocalMenuEntry
import es.upm.sistemabienestaremocional.ui.component.AppBasicScreen
import es.upm.sistemabienestaremocional.ui.component.BasicCard
import es.upm.sistemabienestaremocional.ui.theme.SistemaBienestarEmocionalTheme

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            BasicCard{
                    Text("Settings placeholder")
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview()
{
    val navController = rememberNavController()
    SistemaBienestarEmocionalTheme {
        SettingsScreen(navController)
    }
}