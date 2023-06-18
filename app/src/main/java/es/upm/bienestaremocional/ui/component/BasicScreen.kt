package es.upm.bienestaremocional.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.ui.navigation.BottomBar
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Define the base detail screen used in the app
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBasicScreen(
    navigator: DestinationsNavigator,
    entrySelected: BottomBarDestination?,
    label: Int,
    snackbarHostState: SnackbarHostState? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Scaffold(
        snackbarHost = { snackbarHostState?.let { SnackbarHost(it) } },
        topBar = { CenterAlignedTopAppBar(title = { Text(stringResource(id = label)) }) },
        bottomBar = { BottomBar(navigator, entrySelected) })
    {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
        {
            content()
        }
    }
}

@Preview(group = "Light Theme")
@Composable
fun AppBasicScreenPreview() {
    BienestarEmocionalTheme()
    {
        AppBasicScreen(navigator = EmptyDestinationsNavigator,
            entrySelected = BottomBarDestination.HomeScreen,
            label = BottomBarDestination.HomeScreen.label,
            content = {}
        )
    }
}

@Preview(group = "Dark Theme")
@Composable
fun AppBasicScreenPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true)
    {
        AppBasicScreen(navigator = EmptyDestinationsNavigator,
            entrySelected = BottomBarDestination.HomeScreen,
            label = BottomBarDestination.HomeScreen.label,
            content = {}
        )
    }
}