package es.upm.bienestaremocional.ui.screens.splash

import android.app.NotificationManager
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.firstTimeExecution
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.data.worker.WorkAdministrator
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.ui.notification.Notification
import es.upm.bienestaremocional.ui.screens.destinations.ErrorScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.HomeScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OnboardingScreenDestination
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val notification: Notification,
    private val healthConnectAvailability: HealthConnectAvailability,
    private val appSettings: AppSettings,
    private val appInfo: AppInfo,
    private val notificationManager: NotificationManager,
    private val scheduler: WorkAdministrator,
    private val lastUploadRepository: LastUploadRepository
) : ViewModel() {
    val state: MutableState<SplashState> = mutableStateOf(SplashState.Init)

    private val showOnboarding = runBlocking { appInfo.getFirstTime().first() }

    @Composable
    fun getDarkTheme() = runBlocking { appSettings.getTheme().first() }.themeIsDark()


    fun onInit() {
        state.value = if (healthConnectAvailability == HealthConnectAvailability.INSTALLED) {
            if (!notification.hasNotificationPermission())
                SplashState.NotificationsDialog
            else
                SplashState.Loading
        }
        else
            SplashState.Redirect
    }

    @Composable
    fun OnNotificationsDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.notification.RequestNotificationPermission { state.value = SplashState.Loading }
        else {
            state.value = SplashState.Loading
        }
    }

    suspend fun onLoading() {
        if (appInfo.getFirstTime().first()) {
            firstTimeExecution(
                notificationManager = notificationManager,
                scheduler = scheduler,
                lastUploadRepository = lastUploadRepository
            )
        }
        state.value = SplashState.Redirect
    }

    fun onRedirect(): Direction {
        //redirect to certain screen
        return when (healthConnectAvailability) {
            HealthConnectAvailability.INSTALLED ->
                if (showOnboarding)
                    OnboardingScreenDestination
                else
                    HomeScreenDestination

            else -> ErrorScreenDestination(healthConnectAvailability)
        }
    }
}