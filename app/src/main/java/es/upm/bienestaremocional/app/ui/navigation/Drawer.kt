package es.upm.bienestaremocional.app.ui.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Draws the entire menu
 * @param navigator: manager of app navigation
 * @param entrySelected: entry that the user is visualizing. Null if the user is in entry that
 * isn't part of menu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonModalDrawerSheet(navigator: DestinationsNavigator, entrySelected : MenuEntry?)
{
    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        NavigationDrawerItems(navigator = navigator, entrySelected = entrySelected)
    }
}

@Preview
@Composable
fun CommonModalDrawerSheetPreview()
{
    BienestarEmocionalTheme {
        CommonModalDrawerSheet(navigator = EmptyDestinationsNavigator,
            entrySelected = MenuEntry.HomeScreen
        )
    }
}

@Preview
@Composable
fun CommonModalDrawerSheetPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        CommonModalDrawerSheet(navigator = EmptyDestinationsNavigator,
            entrySelected = MenuEntry.HomeScreen
        )
    }
}