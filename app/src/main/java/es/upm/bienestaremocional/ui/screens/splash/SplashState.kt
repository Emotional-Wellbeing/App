package es.upm.bienestaremocional.ui.screens.splash

sealed class SplashState {
    object Init : SplashState()
    object Loading : SplashState()
    object Redirect : SplashState()
}