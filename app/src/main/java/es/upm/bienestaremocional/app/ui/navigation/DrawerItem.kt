package es.upm.bienestaremocional.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Draw a single element
 * @param entry to be drawn
 * @param selected: if the user is in this entry
 * @param repaintIcon: if the app should repaint the icon for appropriate view
 * @param onClick: function to execute when the user click on this element
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawNavigationDrawerItem(entry: MenuEntry,
                                     selected : Boolean,
                                     repaintIcon: Boolean = true,
                                     onClick: () -> Unit)
{
    NavigationDrawerItem(
        icon = {
            Icon(
                modifier = Modifier
                    .padding(all = 2.dp)
                    .size(size = 28.dp),
                painter = painterResource(entry.iconId),
                contentDescription = null,
                tint = if(repaintIcon) MaterialTheme.colorScheme.onBackground else Color.Unspecified
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

/**
 * Draw menu items
 * @param navigator: manager of app navigation 
 * @param entrySelected: entry that the user is visualizing. Null if the user is in entry that 
 * isn't part of menu
 */
@Composable
fun NavigationDrawerItems(navigator: DestinationsNavigator, entrySelected: MenuEntry?)
{
    MenuEntry.values().forEach {
        element -> DrawNavigationDrawerItem(entry = element, selected = element === entrySelected) {
            navigator.navigate(element.direction)
        }
    }
}

@Preview(group = "Light Theme")
@Composable
fun DrawLocalItemPreview()
{
    BienestarEmocionalTheme {
        DrawNavigationDrawerItem(MenuEntry.HomeScreen, selected = false){}
    }
}

@Preview(group = "Dark Theme")
@Composable
fun DrawLocalItemPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DrawNavigationDrawerItem(MenuEntry.HomeScreen, selected = false){}
    }
}

@Preview(group = "Light Theme")
@Composable
fun DrawItemSelectedPreview()
{
    BienestarEmocionalTheme {
        DrawNavigationDrawerItem(MenuEntry.HistoryScreen, selected = true){}
    }
}

@Preview(group = "Dark Theme")
@Composable
fun DrawItemSelectPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        DrawNavigationDrawerItem(MenuEntry.HistoryScreen, selected = true){}
    }
}

