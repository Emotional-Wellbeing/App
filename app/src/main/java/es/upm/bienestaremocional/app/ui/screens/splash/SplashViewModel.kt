package es.upm.bienestaremocional.app.ui.screens.splash

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.ui.notification.Notification
import es.upm.bienestaremocional.app.ui.screens.destinations.ErrorScreenDestination
import es.upm.bienestaremocional.app.ui.screens.destinations.HomeScreenDestination
import es.upm.bienestaremocional.app.ui.screens.destinations.OnboardingScreenDestination
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val notification: Notification,
    private val scheduler: AlarmScheduler,
    private val healthConnectAvailability : MutableState<HealthConnectAvailability>,
    private val appSettings: AppSettings,
) : ViewModel()
{
    val state : MutableState<SplashState> = mutableStateOf(
        if(!notification.hasNotificationPermission())
            SplashState.NotificationsDialog
        else if (!scheduler.canScheduleExactly())
            SplashState.ExactDialog
        else
            SplashState.NoDialog
    )

    private val showOnboarding = appSettings.getFirstTimeValue()

    @Composable
    fun getDarkTheme() = appSettings.getThemeValue().themeIsDark()

    @Composable
    fun NotificationsDialogAction()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.notification.RequestNotificationPermission{
                permissionGranted ->
                if (permissionGranted && !scheduler.canScheduleExactly())
                    state.value = SplashState.ExactDialog
                else
                    state.value = SplashState.NoDialog
            }
        else
        {
            if (!scheduler.canScheduleExactly())
                state.value = SplashState.ExactDialog
            else
                state.value = SplashState.NoDialog
        }
    }

    fun exactDialogAction(confirm: Boolean)
    {
        if (confirm && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            this.scheduler.requestPermissions()
        state.value = SplashState.NoDialog
    }

    fun noDialogAction() : Direction
    {
        //redirect to certain screen
        return when (healthConnectAvailability.value)
        {
            HealthConnectAvailability.INSTALLED ->
                if (showOnboarding)
                    OnboardingScreenDestination(MainApplication.windowSize!!)
                else
                    HomeScreenDestination
            else -> ErrorScreenDestination(healthConnectAvailability.value)
        }
    }
}