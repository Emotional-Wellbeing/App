package es.upm.bienestaremocional.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Draw the bottom bar of the application
 * @param navigator: [DestinationsNavigator] to move between sceens
 * @param entrySelected: [BottomBarDestination] to mark the selected entry
 */
@Composable
fun BottomBar(
    navigator: DestinationsNavigator,
    entrySelected: BottomBarDestination?
) {
    NavigationBar {
        BottomBarDestination.values().forEach { destination ->
            BottomBarItem(
                icon = destination.icon,
                label = destination.label,
                selected = destination === entrySelected,
                onClick = {
                    navigator.navigate(destination.direction) {
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

/**
 * Draw a single element inside a Row
 * @param icon: DrawableRes with the icon of the screen
 * @param label: StringRes with the label of the screen
 * @param selected: If the current screen is selected or not
 * @param onClick : fun to execute when the user presses the button
 */
@Composable
private fun RowScope.BottomBarItem(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                modifier = Modifier
                    .size(size = 24.dp),
                painter = painterResource(icon),
                contentDescription = stringResource(label)
            )
        },
        label = {
            Text(
                text = stringResource(id = label),
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Visible,
                maxLines = 1
            )
        },
    )
}

@Preview(group = "Light Theme")
@Composable
fun BottomBarPreview() {
    BienestarEmocionalTheme {
        BottomBar(EmptyDestinationsNavigator, BottomBarDestination.HomeScreen)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun BottomBarPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        BottomBar(EmptyDestinationsNavigator, BottomBarDestination.HomeScreen)
    }
}

@Preview(group = "Light Theme")
@Composable
fun BottomBarItemSelectedPreview() {
    BienestarEmocionalTheme {
        Surface {
            //extracted from https://m3.material.io/components/navigation-bar/specs
            Row(Modifier.height(80.dp))
            {
                BottomBarItem(
                    selected = true,
                    onClick = {},
                    icon = BottomBarDestination.HomeScreen.icon,
                    label = BottomBarDestination.HomeScreen.label
                )
            }
        }
    }
}

@Preview(group = "Dark Theme")
@Composable
fun BottomBarItemSelectedPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            Row(Modifier.height(80.dp))
            {
                BottomBarItem(
                    selected = true,
                    onClick = {},
                    icon = BottomBarDestination.HomeScreen.icon,
                    label = BottomBarDestination.HomeScreen.label
                )
            }
        }
    }
}

@Preview(group = "Light Theme")
@Composable
fun BottomBarItemNotSelectedPreview() {
    BienestarEmocionalTheme {
        Surface {
            Row(Modifier.height(80.dp))
            {
                BottomBarItem(
                    selected = false,
                    onClick = {},
                    icon = BottomBarDestination.HomeScreen.icon,
                    label = BottomBarDestination.HomeScreen.label
                )
            }
        }
    }
}

@Preview(group = "Dark Theme")
@Composable
fun BottomBarItemNotSelectedPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            Row(Modifier.height(80.dp))
            {
                BottomBarItem(
                    selected = false,
                    onClick = {},
                    icon = BottomBarDestination.HomeScreen.icon,
                    label = BottomBarDestination.HomeScreen.label
                )
            }
        }

    }
}