package es.upm.bienestaremocional.ui.screens.permission

import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.firstTimeExecution
import es.upm.bienestaremocional.data.worker.WorkAdministrator
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.ui.notification.Notification
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val notification: Notification,
    private val notificationManager: NotificationManager,
    private val workAdministrator: WorkAdministrator,
    private val lastUploadRepository: LastUploadRepository
) : ViewModel() {
    @Composable
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun RequestNotificationPermission(onResult: (Boolean) -> Unit) {
        notification.RequestNotificationPermission(onResult = onResult)
    }

    fun onFinish() {
        viewModelScope.launch {
            firstTimeExecution(
                notificationManager = notificationManager,
                workAdministrator = workAdministrator,
                lastUploadRepository = lastUploadRepository
            )
            //Execute app usage
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                workAdministrator.scheduleUploadUsageInfoWorker()
            }
        }
    }
}