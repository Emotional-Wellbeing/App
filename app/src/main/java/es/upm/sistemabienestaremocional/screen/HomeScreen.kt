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
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.navigation.Drawer
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Welcome screen shown when the app is first launched.
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(onSleepClick : () -> Unit, onPrivacyClick: () -> Unit)
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
                Drawer()
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                elevation = 10.dp
            )
            {
                Column(
                    modifier = Modifier.padding(15.dp)
                )
                {
                    Text("Custom card")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onSleepClick)
            {
                Text(text = stringResource(id = R.string.sleep))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onPrivacyClick)
            {
                Text(text = stringResource(id = R.string.privacy_policy))
            }
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview()
{
    SistemaBienestarEmocionalTheme{
        HomeScreen(onSleepClick = {}, onPrivacyClick = {})
    }
}
