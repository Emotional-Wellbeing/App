package es.upm.bienestaremocional.ui.screens.splash

sealed class SplashState {
    object NoDialog : SplashState()
    object NotificationsDialog : SplashState()
}