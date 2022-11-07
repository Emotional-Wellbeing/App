package es.upm.bienestaremocional.core.healthconnect.ui

import android.os.RemoteException
import androidx.compose.runtime.MutableState
import androidx.health.connect.client.permission.HealthPermission
import androidx.lifecycle.ViewModel
import es.upm.bienestaremocional.core.healthconnect.data.HealthConnectManager
import java.io.IOException
import java.util.*

abstract class HealthConnectViewModel(protected val healthConnectManager: HealthConnectManager):
    ViewModel()
{
    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    //properties of the subclasses
    abstract val permissions: Set<HealthPermission>
    abstract var permissionsGranted : MutableState<Boolean>
        protected set
    abstract var uiState: UiState
        protected set

    /**
     * Provides permission check and error handling for Health Connect suspend function calls.
     *
     * Permissions are checked prior to execution of [block], and if all permissions aren't granted
     * the [block] won't be executed, and [permissionsGranted] will be set to false, which will
     * result in the UI showing the permissions button.
     *
     * Where an error is caught, of the type Health Connect is known to throw, [uiState] is set to
     * [UiState.Error], which results in the snackbar being used to show the error message.
     */
    protected suspend fun tryWithPermissionsCheck(block: suspend () -> Unit)
    {
        permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
        uiState = try
        {
            if (permissionsGranted.value)
            {
                block()
                UiState.Success
            }
            else
                UiState.NotEnoughPermissions
        } catch (remoteException: RemoteException)
        {
            UiState.Error(remoteException)
        } catch (securityException: SecurityException)
        {
            UiState.Error(securityException)
        } catch (ioException: IOException)
        {
            UiState.Error(ioException)
        } catch (illegalStateException: IllegalStateException)
        {
            UiState.Error(illegalStateException)
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