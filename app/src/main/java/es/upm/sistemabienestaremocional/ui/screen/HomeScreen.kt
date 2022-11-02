package es.upm.sistemabienestaremocional.ui.screen

import android.annotation.SuppressLint
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
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.navigation.LocalMenuEntry
import es.upm.sistemabienestaremocional.ui.component.AppBasicScreen
import es.upm.sistemabienestaremocional.ui.component.BasicCard
import es.upm.sistemabienestaremocional.ui.theme.SistemaBienestarEmocionalTheme

/**
 * Welcome screen shown when the app is first launched.
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, onSleepClick : () -> Unit)
{
    AppBasicScreen(navController = navController, entrySelected = LocalMenuEntry.HomeScreen)
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
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview()
{
    //nav controller init
    val navController = rememberNavController()

    SistemaBienestarEmocionalTheme{
        HomeScreen(
            navController = navController,
            onSleepClick = {})
    }
}
