
package es.upm.bienestaremocional.ui.screen.sleep

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.upm.bienestaremocional.data.healthconnect.CommonViewModel
import es.upm.bienestaremocional.data.healthconnect.HealthConnectManager
import es.upm.bienestaremocional.data.types.SleepSessionData
import kotlinx.coroutines.launch

class SleepSessionViewModel(healthConnectManager: HealthConnectManager) :
    CommonViewModel(healthConnectManager)
{

    override val permissions = setOf(
        HealthPermission.createReadPermission(SleepSessionRecord::class),
        HealthPermission.createReadPermission(SleepStageRecord::class),
    )

    override var permissionsGranted = mutableStateOf(false)

    override var uiState: UiState by mutableStateOf(UiState.Uninitialized)

    var sessionsList: MutableState<List<SleepSessionData>> = mutableStateOf(listOf())

    /**
     * Tries to load sleep sessions
     */
    fun initialLoad()
    {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                sessionsList.value = healthConnectManager.readSleepSessions()
            }
        }
    }
}

//this class is used to init SleepSessionViewModel
class SleepSessionViewModelFactory(private val healthConnectManager: HealthConnectManager) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(SleepSessionViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return SleepSessionViewModel(healthConnectManager = healthConnectManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
