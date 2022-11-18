package es.upm.bienestaremocional.core.ui.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonModalDrawerSheet(navController: NavController, entrySelected : MenuEntry?)
{
    //Modifier.alpha(0.66f) to obtain transparent menu
    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        NavigationDrawerItems(navController = navController, entrySelected = entrySelected)
    }
}

@Preview
@Composable
fun CommonModalDrawerSheetPreview()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme {
        CommonModalDrawerSheet(navController = navController,
            entrySelected = LocalMenuEntry.HomeScreen)
    }
}

@Preview
@Composable
fun CommonModalDrawerSheetPreviewDarkTheme()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme(darkTheme = true) {
        CommonModalDrawerSheet(navController = navController,
            entrySelected = LocalMenuEntry.HomeScreen)
    }
}