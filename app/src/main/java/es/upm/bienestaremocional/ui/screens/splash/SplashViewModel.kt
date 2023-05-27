package es.upm.bienestaremocional.ui.screens.splash

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.settings.AppSettings
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
    private val healthConnectAvailability : MutableState<HealthConnectAvailability>,
    private val appSettings: AppSettings,
    private val appInfo: AppInfo
) : ViewModel()
{
    val state : MutableState<SplashState> = mutableStateOf(
        if(!notification.hasNotificationPermission())
            SplashState.NotificationsDialog
        else
            SplashState.NoDialog
    )

    private val showOnboarding = runBlocking { appInfo.getFirstTime().first() }

    @Composable
    fun getDarkTheme() = runBlocking { appSettings.getTheme().first() }.themeIsDark()

    @Composable
    fun NotificationsDialogAction()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.notification.RequestNotificationPermission { state.value = SplashState.NoDialog }
        else
        {
            state.value = SplashState.NoDialog
        }
    }

    fun noDialogAction() : Direction
    {
        //redirect to certain screen
        return when (healthConnectAvailability.value)
        {
            HealthConnectAvailability.INSTALLED ->
                if (showOnboarding)
                    OnboardingScreenDestination
                else
                    HomeScreenDestination
            else -> ErrorScreenDestination(healthConnectAvailability.value)
        }
    }
}