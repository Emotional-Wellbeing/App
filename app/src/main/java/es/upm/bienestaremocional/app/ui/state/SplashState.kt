package es.upm.bienestaremocional.app.ui.state

sealed class SplashState
{
    object SkipDialog : SplashState()
    object LaunchDialog: SplashState()
}