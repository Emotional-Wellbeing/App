package es.upm.bienestaremocional.app.ui.viewmodel

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ramcosta.composedestinations.spec.Direction
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.ui.notification.Notification
import es.upm.bienestaremocional.app.ui.screen.destinations.ErrorScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.HomeScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.OnboardingScreenDestination
import es.upm.bienestaremocional.app.ui.state.SplashState
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectAvailability

class SplashViewModel(private val notification: Notification,
                      private val scheduler: AlarmScheduler,
                      private val healthConnectAvailability : MutableState<HealthConnectAvailability>,
                      appSettings: AppSettingsInterface,
) : ViewModel()
{
    companion object
    {
        /**
         * Factory class to instance [SettingsViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                SplashViewModel(notification = MainApplication.notification,
                    scheduler = MainApplication.alarmScheduler,
                    healthConnectAvailability = MainApplication.healthConnectManager.availability,
                    appSettings = MainApplication.appSettings)
            }
        }
    }


    val state : MutableState<SplashState> = mutableStateOf(
        if(!MainApplication.notification.hasNotificationPermission())
            SplashState.NotificationsDialog
        else if (!MainApplication.alarmScheduler.canScheduleExactly())
            SplashState.ExactDialog
        else
            SplashState.NoDialog
    )

    private val showOnboarding = appSettings.getFirstTimeValue()

    @Composable
    fun NotificationsDialogAction()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.notification.RequestNotificationPermission{
                permissionGranted ->
                if (permissionGranted && !MainApplication.alarmScheduler.canScheduleExactly())
                    state.value = SplashState.ExactDialog
                else
                    state.value = SplashState.NoDialog
            }
        else
        {
            if (!MainApplication.alarmScheduler.canScheduleExactly())
                state.value = SplashState.ExactDialog
            else
                state.value = SplashState.NoDialog
        }
    }

    fun exactDialogAction(confirm: Boolean)
    {
        if (confirm)
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