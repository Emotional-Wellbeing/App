package es.upm.bienestaremocional.core.extraction.healthconnect.ui

import android.os.RemoteException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

abstract class HealthConnectViewModel: ViewModel()
{
    var uiState: UiState by mutableStateOf(UiState.Uninitialized)
        protected set

    val permissionLauncher = PermissionController.createRequestPermissionResultContract()

    fun readData(healthConnectSource: HealthConnectSource, data: MutableState<List<Record>>)
    {
        viewModelScope.launch {
            uiState = try {
                if (healthConnectSource.readPermissionsCheck())
                {
                    data.value = healthConnectSource.readSource()
                    UiState.Success
                }
                else
                    UiState.NotEnoughPermissions
            }
            catch (remoteException: RemoteException) {
                UiState.Error(remoteException)
            }
            catch (securityException: SecurityException) {
                UiState.Error(securityException)
            }
            catch (ioException: IOException) {
                UiState.Error(ioException)
            }
            catch (illegalStateException: IllegalStateException) {
                UiState.Error(illegalStateException)
            }
        }
    }

    sealed class UiState
    {
        object Uninitialized : UiState()
        object Success : UiState()
        object NotEnoughPermissions : UiState()

        // A random UUID is used in each Error object to allow errors to be uniquely identified,
        // and recomposition won't result in multiple snackbars.
        data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : UiState()
    }

}