package es.upm.bienestaremocional.app.ui.navigation

import es.upm.bienestaremocional.R

/**
 * This enum contains the app's menu entries
 * @param route: The route string used for Compose navigation
 * @param labelId: Resource with the label string
 * @param iconId: Resource with the icon drawable
 */
enum class MenuEntry(val route: String, val labelId: Int, val iconId: Int)
{
    HomeScreen(route = Screen.HomeScreen.route,
        labelId = R.string.home_screen_label,
        iconId = R.drawable.home),

    HistoryScreen(route = Screen.HistoryScreen.route,
        labelId = R.string.history_screen_label,
        iconId = R.drawable.monitor_heart),

    EvolutionScreen(route = Screen.EvolutionScreen.route,
        labelId = R.string.evolution_screen_label,
        iconId = R.drawable.ssid_chart),

    TrendsScreen(route = Screen.TrendsScreen.route,
        labelId = R.string.trends_screen_label,
        iconId = R.drawable.people_alt),

    SettingsScreen(route = "settings_screen",
        labelId = R.string.settings_screen_label,
        iconId = R.drawable.settings),

}