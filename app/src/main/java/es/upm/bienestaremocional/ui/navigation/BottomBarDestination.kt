package es.upm.bienestaremocional.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.Direction
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.screens.destinations.HistoryScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.HomeScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.SettingsScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.TrendsScreenDestination

/**
 * This enum contains the app's menu entries
 * @param direction: The route string used for Compose destination
 * @param label: Resource with the label string
 * @param icon: Resource with the icon drawable
 */
enum class BottomBarDestination(val direction: Direction,
                                @StringRes val label: Int,
                                @DrawableRes val icon: Int)
{
    HomeScreen(direction = HomeScreenDestination,
        label = R.string.home_screen_label,
        icon = R.drawable.home),

    HistoryScreen(direction = HistoryScreenDestination(null),
        label = R.string.history_screen_label,
        icon = R.drawable.ssid_chart),

    TrendsScreen(direction = TrendsScreenDestination,
        label = R.string.trends_screen_label,
        icon = R.drawable.people_alt),

    SettingsScreen(direction = SettingsScreenDestination,
        label = R.string.settings_screen_label,
        icon = R.drawable.settings),
}