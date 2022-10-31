package es.upm.sistemabienestaremocional.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


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
