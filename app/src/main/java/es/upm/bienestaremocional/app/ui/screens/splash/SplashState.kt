package es.upm.bienestaremocional.app.ui.screens.splash

sealed class SplashState
{
    object NoDialog : SplashState()
    object NotificationsDialog: SplashState()
}