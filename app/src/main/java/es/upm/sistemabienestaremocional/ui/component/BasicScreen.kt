package es.upm.sistemabienestaremocional.ui.component

import android.annotation.SuppressLint
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
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.navigation.CommonModalDrawerSheet
import es.upm.sistemabienestaremocional.navigation.LocalMenuEntry
import es.upm.sistemabienestaremocional.navigation.MenuEntry
import es.upm.sistemabienestaremocional.ui.theme.SistemaBienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Define the base detail screen used in the app
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppBasicScreen(navController: NavController,
                 entrySelected: MenuEntry,
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
                        title = { Text(stringResource(entrySelected.labelId)) },
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
            /*Scaffold(
                topBar =
                {
                    TopAppBar(
                        title = { Text(stringResource(id = idTitle)) },
                        navigationIcon =
                        {
                            IconButton(onClick = onBackClick)
                            {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = stringResource(R.string.go_back)
                                )
                            }
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxSize(),
                    //padding is not realised here because it affect top bar
                content = content
            )*/
}

@Preview
@Composable
fun AppBasicScreenPreview()
{
    val navController = rememberNavController()
    SistemaBienestarEmocionalTheme()
    {
        AppBasicScreen(navController = navController,
            entrySelected = LocalMenuEntry.HomeScreen,
            content = {}
        )
    }
}