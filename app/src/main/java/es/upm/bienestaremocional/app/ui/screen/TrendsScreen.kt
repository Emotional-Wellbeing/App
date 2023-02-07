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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.app.ui.navigation.MenuEntry
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Plots graphics about all users (trends)
 */
@Destination
@Composable
fun TrendsScreen(navigator: DestinationsNavigator)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = MenuEntry.TrendsScreen,
        label = MenuEntry.TrendsScreen.labelId)
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

    BienestarEmocionalTheme {
        TrendsScreen(EmptyDestinationsNavigator)
    }
}

@Preview(showBackground = true)
@Composable
fun TrendsScreenPreviewDarkTheme()
{

    BienestarEmocionalTheme(darkTheme = true) {
        TrendsScreen(EmptyDestinationsNavigator)
    }
}