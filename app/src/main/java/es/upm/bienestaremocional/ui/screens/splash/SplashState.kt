package es.upm.bienestaremocional.ui.screens.splash

sealed class SplashState
{
    object Init : SplashState()
    object NoDialog : SplashState()
}