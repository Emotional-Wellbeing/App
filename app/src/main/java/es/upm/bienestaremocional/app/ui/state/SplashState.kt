package es.upm.bienestaremocional.app.ui.state

sealed class SplashState
{
    object NoDialog : SplashState()
    object ExactDialog: SplashState()
    object NotificationsDialog: SplashState()
}