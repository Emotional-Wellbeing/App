package es.upm.bienestaremocional.app.ui.navigation

import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.ui.screen.destinations.*

/**
 * This enum contains the app's menu entries
 * @param direction: The route string used for Compose destination
 * @param labelId: Resource with the label string
 * @param iconId: Resource with the icon drawable
 */
enum class MenuEntry(val direction: DirectionDestination, val labelId: Int, val iconId: Int)
{
    HomeScreen(direction = HomeScreenDestination,
        labelId = R.string.home_screen_label,
        iconId = R.drawable.home),

    HistoryScreen(direction = HistoryScreenDestination,
        labelId = R.string.history_screen_label,
        iconId = R.drawable.monitor_heart),

    EvolutionScreen(direction = EvolutionScreenDestination,
        labelId = R.string.evolution_screen_label,
        iconId = R.drawable.ssid_chart),

    TrendsScreen(direction = TrendsScreenDestination,
        labelId = R.string.trends_screen_label,
        iconId = R.drawable.people_alt),

    SettingsScreen(direction = SettingsScreenDestination,
        labelId = R.string.settings_screen_label,
        iconId = R.drawable.settings),

}