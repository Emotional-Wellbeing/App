package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Welcome screen shown when the app is first launched.
 */

@Composable
fun HomeScreen(navController: NavController,
               onSleepClick : () -> Unit,
               onHeartrateClick : () -> Unit)
{
    AppBasicScreen(navController = navController,
        entrySelected = LocalMenuEntry.HomeScreen,
        label = R.string.app_name)
    {
        Column(
            modifier = Modifier
                .fillMaxSize() //need it for fill all remaining screen
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            BasicCard{
                Text("Message placeholder")
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicCard{
                Text("Today stats placeholder")
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicCard{
                Text("PHQ-9 placeholder")
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicCard{
                Text("Feedback placeholder")
            }

            Spacer(modifier = Modifier.height(16.dp))

            BasicCard{
                Text("Last week stats placeholder")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onSleepClick)
            {
                Text(text = stringResource(id = R.string.sleep))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onHeartrateClick)
            {
                Text(text = stringResource(id = R.string.heart_rate))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview()
{
    //nav controller init
    val navController = rememberNavController()

    BienestarEmocionalTheme{
        HomeScreen(
            navController = navController,
            onSleepClick = {},
            onHeartrateClick = {})
    }
}

// this preview has a bug rendering the background of the screen
@Preview(showBackground = true)
@Composable
fun HomeScreenPreviewDarkTheme()
{
    //nav controller init
    val navController = rememberNavController()

    BienestarEmocionalTheme(darkTheme = true)
    {
        HomeScreen(
            navController = navController,
            onSleepClick = {},
            onHeartrateClick = {})
    }
}