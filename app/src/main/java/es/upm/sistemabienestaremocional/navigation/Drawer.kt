package es.upm.sistemabienestaremocional.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

@Composable
fun Drawer(navController: NavController)
{
    Column(
        modifier = Modifier
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp)
    )
    {
        GetDrawerItems(navController = navController)
    }
}

@Preview
@Composable
fun DrawerPreview()
{
    val navController = rememberNavController()
    SistemaBienestarEmocionalTheme {
        Drawer(navController = navController)
    }
}
