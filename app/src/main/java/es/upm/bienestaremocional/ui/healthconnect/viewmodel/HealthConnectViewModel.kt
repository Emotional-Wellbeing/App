package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import android.os.RemoteException
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.ui.component.ViewModelData
import es.upm.bienestaremocional.ui.healthconnect.UiState
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Implements [HealthConnectViewModel] and add some shared variables
 */
abstract class HealthConnectViewModel<T : Record> : ViewModel() {
    /**
     * Holds UiState to show (or not) data, request permission button, exceptions...
     */
    var uiState: UiState by mutableStateOf(UiState.Uninitialized)
        protected set

    protected val elements: MutableState<List<T>> = mutableStateOf(listOf())

    /**
     * Launcher to request permissions
     */
    val permissionLauncher = PermissionController.createRequestPermissionResultContract()

    fun readData(healthConnectSource: HealthConnectSource<T>) {
        viewModelScope.launch {
            uiState = try {
                if (healthConnectSource.readPermissionsCheck()) {
                    elements.value = healthConnectSource.readSource()
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

    @Composable
    abstract fun getViewModelData(): ViewModelData<T>
}