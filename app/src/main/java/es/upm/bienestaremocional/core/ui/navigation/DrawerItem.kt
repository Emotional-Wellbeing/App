package es.upm.bienestaremocional.core.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawNavigationDrawerItem(entry: MenuEntry,
                                     selected : Boolean,
                                     repintIcon: Boolean = true,
                                     onClick: () -> Unit)
{
    /**
     * @Todo create text size options responsive
     * @Todo create color options according to light/dark theme
     */

    NavigationDrawerItem(
        icon = {
            Icon(
                modifier = Modifier
                    .padding(all = 2.dp)
                    .size(size = 28.dp),
                painter = painterResource(entry.iconId),
                contentDescription = null,
                tint = if(repintIcon) MaterialTheme.colorScheme.onBackground else Color.Unspecified
            )
        },
        label = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = entry.labelId),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
        selected = selected,
        onClick = onClick
    )
}

@Composable
fun NavigationDrawerItems(navController: NavController, entrySelected: MenuEntry?)
{
    val context = LocalContext.current

    LocalMenuEntry.values().forEach {
        element -> DrawNavigationDrawerItem(entry = element, selected = element === entrySelected) {
            navController.navigate(element.route)
        }
    }

    ForeignMenuEntry.values().forEach {
        element -> DrawNavigationDrawerItem(
                        entry = element,
                        selected = element === entrySelected,
                        repintIcon = false)
                    {
                        openForeignActivity(context = context, action = element.action)
                    }
    }
}

@Preview
@Composable
fun DrawLocalItemPreview()
{
    BienestarEmocionalTheme {
        DrawNavigationDrawerItem(LocalMenuEntry.HomeScreen, selected = false){}
    }
}

@Preview
@Composable
fun DrawLocalItemPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DrawNavigationDrawerItem(LocalMenuEntry.HomeScreen, selected = false){}
    }
}

@Preview
@Composable
fun DrawForeignItemPreview()
{
    BienestarEmocionalTheme {
        DrawNavigationDrawerItem(
            ForeignMenuEntry.HealthConnectScreen,
            selected = false,
            repintIcon = false)
        {}
    }
}

@Preview
@Composable
fun DrawForeignItemPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DrawNavigationDrawerItem(
            ForeignMenuEntry.HealthConnectScreen,
            selected = false,
            repintIcon = false)
        {}
    }
}