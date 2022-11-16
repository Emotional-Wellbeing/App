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
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Plots graphics about all users (trends)
 */

@Composable
fun TrendsScreen(navController: NavController)
{
    AppBasicScreen(navController = navController, entrySelected = LocalMenuEntry.TrendsScreen)
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
                Text("Trends placeholder")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrendsScreenPreview()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme {
        TrendsScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun TrendsScreenPreviewDarkTheme()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme(darkTheme = true) {
        TrendsScreen(navController)
    }
}