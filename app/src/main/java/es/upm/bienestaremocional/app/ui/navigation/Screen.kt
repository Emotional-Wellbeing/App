package es.upm.bienestaremocional.app.ui.navigation

/**
 * Contains all Screens in the app
 * @param route: The route string used for Compose navigation
 */

enum class Screen (val route: String)
{
    SplashScreen(route = "splash_screen"),
    ErrorScreen(route = "error_screen"),
    OnboardingScreen(route = "onboarding_screen"),
    HomeScreen(route = "home_screen"),
    HistoryScreen(route = "history_screen"),
    EvolutionScreen(route = "evolution_screen"),
    TrendsScreen(route= "trends_screen"),
    SettingsScreen(route = "settings_screen"),
    PrivacyPolicyScreen(route = "privacy_policy_screen"),
    AboutScreen(route = "about_screen"),
    CreditsScreen(route = "credits_screen"),
    MyDataScreen(route = "my_data_screen")
}