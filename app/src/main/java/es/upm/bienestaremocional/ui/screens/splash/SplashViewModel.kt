package es.upm.bienestaremocional.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.ui.screens.destinations.ErrorScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.HomeScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OnboardingScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val healthConnectAvailability: HealthConnectAvailability,
    private val appSettings: AppSettings,
    private val appInfo: AppInfo,
) : ViewModel() {
    private val _state: MutableStateFlow<SplashState> =
        MutableStateFlow(SplashState.Init)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private val showOnboarding = runBlocking { appInfo.getFirstTime().first() }

    @Composable
    fun getDarkTheme() = runBlocking { appSettings.getTheme().first() }.themeIsDark()

    fun onLoading() {
        _state.value = SplashState.Redirect
    }

    fun onRedirect(): Direction {
        //redirect to certain screen
        return when (healthConnectAvailability) {
            HealthConnectAvailability.INSTALLED ->
                if (showOnboarding)
                    OnboardingScreenDestination(standalone = false)
                else
                    HomeScreenDestination

            else -> ErrorScreenDestination(healthConnectAvailability)
        }
    }
    fun onInit() {
        viewModelScope.launch {
            _state.value = if (healthConnectAvailability == HealthConnectAvailability.INSTALLED) {
                SplashState.Loading
            }
            else
                SplashState.Redirect
        }
    }
}