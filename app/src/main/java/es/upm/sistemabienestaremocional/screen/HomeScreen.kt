package es.upm.sistemabienestaremocional.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.component.BasicCard
import es.upm.sistemabienestaremocional.navigation.Drawer
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Welcome screen shown when the app is first launched.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, onSleepClick : () -> Unit)
{
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar =
            {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon =
                        {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch{
                                        scaffoldState.drawerState.open()
                                    }
                                }
                            )
                            {
                                Icon(
                                    imageVector = Icons.Rounded.Menu,
                                    contentDescription = stringResource(id = R.string.menu)
                                )
                            }
                        }
                )
            },
        drawerContent =
            {
                Drawer(navController = navController)
            }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
