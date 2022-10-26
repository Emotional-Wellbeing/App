package es.upm.sistemabienestaremocional.navigation

/**
 * Contains all Screens in the app
 * @param route: The route string used for Compose navigation
 */

enum class Screen (val route: String)
{
    MainScreen("main_screen"),
    ErrorScreen("error_screen"),
    HomeScreen("home_screen"),
    PrivacyPolicyScreen("privacy_policy_screen"),
    SleepScreen("sleep_screen")
}