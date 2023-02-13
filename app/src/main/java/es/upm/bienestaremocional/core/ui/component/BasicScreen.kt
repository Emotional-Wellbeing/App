package es.upm.bienestaremocional.core.ui.component

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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.navigation.CommonModalDrawerSheet
import es.upm.bienestaremocional.app.ui.navigation.MenuEntry
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Define the base detail screen used in the app
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBasicScreen(navigator: DestinationsNavigator,
                   entrySelected: MenuEntry?,
                   label: Int,
                   scope : CoroutineScope = rememberCoroutineScope(),
                   snackbarHostState: SnackbarHostState? = null,
                   content: @Composable RowScope.() -> Unit)
{
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    //top bar doesn't work well
    Scaffold(snackbarHost = {snackbarHostState?.let { SnackbarHost(it)}})
    {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent =
            {
                CommonModalDrawerSheet(navigator = navigator, entrySelected = entrySelected)
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


}

@Preview(group = "Light Theme")
@Composable
fun AppBasicScreenPreview()
{
    BienestarEmocionalTheme()
    {
        AppBasicScreen(navigator = EmptyDestinationsNavigator,
            entrySelected = MenuEntry.HomeScreen,
            label = MenuEntry.HomeScreen.labelId,
            content = {}
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
fun AppBasicScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        AppBasicScreen(navigator = EmptyDestinationsNavigator,
            entrySelected = MenuEntry.HomeScreen,
            label = MenuEntry.HomeScreen.labelId,
            content = {}
        )
    }
}