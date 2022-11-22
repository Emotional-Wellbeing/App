package es.upm.bienestaremocional.core.ui.navigation

import es.upm.bienestaremocional.R

/**
 * This interface contains the common elements of menu entries.
 * These elements are label and icon, and they are used to show the menu entry in the drawer
 */
interface MenuEntry
{
    val labelId: Int
    val iconId: Int
}

/**
 * This enum contains the app's menu entries
 * @param route: The route string used for Compose navigation
 */
enum class LocalMenuEntry(val route: String): MenuEntry
{
    HomeScreen(route = Screen.HomeScreen.route)
    {
        override val labelId: Int
            get() = R.string.home_screen_label
        override val iconId: Int
            get() = R.drawable.home
    },

    HistoryScreen(route = Screen.HistoryScreen.route)
    {
        override val labelId: Int
            get() = R.string.history_screen_label
        override val iconId: Int
            get() = R.drawable.monitor_heart
    },

    EvolutionScreen(route = Screen.EvolutionScreen.route)
    {
        override val labelId: Int
            get() = R.string.evolution_screen_label
        override val iconId: Int
            get() = R.drawable.ssid_chart
    },

    TrendsScreen(route = Screen.TrendsScreen.route)
    {
        override val labelId: Int
            get() = R.string.trends_screen_label
        override val iconId: Int
            get() = R.drawable.people_alt

    },
    SettingsScreen(route = "settings_screen")
    {
        override val labelId: Int
            get() = R.string.settings_screen_label
        override val iconId: Int
            get() = R.drawable.settings
    },

    PrivacyPolicyScreen(route = Screen.PrivacyPolicyScreen.route)
    {
        override val labelId: Int
            get() = R.string.privacy_policy_screen_label
        override val iconId: Int
            get() = R.drawable.security
    },
}

/**
 * This enum contains the menu entries that open other app.
 * For example, HealthConnectScreen opens ACTION_HEALTH_CONNECT_SETTINGS activity from
 * Health Connect App
 * @param action: The name of the activity that should be opened
 */
enum class ForeignMenuEntry(val action : String): MenuEntry
{
    HealthConnectScreen(action = "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS")
    {
        override val labelId: Int
            get() = R.string.health_connect_settings_label
        override val iconId: Int
            get() = R.drawable.health_connect_logo
    },
}