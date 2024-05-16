package es.upm.bienestaremocional.ui.screens.splash

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.data.worker.WorkAdministrator
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
    private val workAdministrator: WorkAdministrator,
) : ViewModel() {
    private val _state: MutableStateFlow<SplashState> =
        MutableStateFlow(SplashState.Init)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private val showOnboarding = runBlocking { appInfo.getFirstTime().first() }

    @Composable
    fun getDarkTheme() = runBlocking { appSettings.getTheme().first() }.themeIsDark()

    suspend fun onLoading() {
        //Execute app usage only if is not first execution. In first execution the permission
        //should be required before scheduling
        if (!appInfo.getFirstTime().first()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                workAdministrator.scheduleUploadUsageInfoWorker()
            }
        }
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