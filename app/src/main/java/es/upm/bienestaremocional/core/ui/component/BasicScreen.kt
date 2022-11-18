package es.upm.bienestaremocional.core.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.ui.navigation.CommonModalDrawerSheet
import es.upm.bienestaremocional.core.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.core.ui.navigation.MenuEntry
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Define the base detail screen used in the app
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBasicScreen(navController: NavController,
                   entrySelected: MenuEntry?,
                   label: Int,
                   content: @Composable RowScope.() -> Unit)
{
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent =
        {
            CommonModalDrawerSheet(navController = navController, entrySelected = entrySelected)
        },
        content =
        {
            //all content in this screen belongs to a general column and two rows
            //first row is por top bar
            Column(modifier = Modifier.fillMaxSize())
            {
                Row(modifier = Modifier.fillMaxWidth())
                {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(id = label)) },
                        navigationIcon =
                        {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isOpen)
                                            drawerState.close()
                                        else
                                            drawerState.open()
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
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    content = content,
                )
            }
        }
    )
}

@Preview
@Composable
fun AppBasicScreenPreview()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme()
    {
        AppBasicScreen(navController = navController,
            entrySelected = LocalMenuEntry.HomeScreen,
            label = LocalMenuEntry.HomeScreen.labelId,
            content = {}
        )
    }
}

@Preview
@Composable
fun AppBasicScreenPreviewDarkTheme()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme(darkTheme = true)
    {
        AppBasicScreen(navController = navController,
            entrySelected = LocalMenuEntry.HomeScreen,
            label = LocalMenuEntry.HomeScreen.labelId,
            content = {}
        )
    }
}