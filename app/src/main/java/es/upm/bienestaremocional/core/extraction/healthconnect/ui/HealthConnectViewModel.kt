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
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Implements [HealthConnectViewModelInterface] and add some shared variables
 */
abstract class HealthConnectViewModel: ViewModel(), HealthConnectViewModelInterface
{
    /**
     * Holds UiState to show (or not) data, request permission button, exceptions...
     */
    var uiState: UiState by mutableStateOf(UiState.Uninitialized)
        protected set

    /**
     * Launcher to request permissions
     */
    val permissionLauncher = PermissionController.createRequestPermissionResultContract()

    override fun readData(healthConnectSource: HealthConnectSourceInterface,
                          data: MutableState<List<Record>>)
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

}