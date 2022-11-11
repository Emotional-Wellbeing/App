package es.upm.bienestaremocional.core.extraction.healthconnect.data

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission

// The minimum android level that can use Health Connect
const val MIN_SUPPORTED_SDK = Build.VERSION_CODES.O_MR1

/**
 * Health Connect requires that the underlying Healthcore APK is installed on the device.
 * [HealthConnectAvailability] represents whether this APK is indeed installed, whether it is not
 * installed but supported on the device, or whether the device is not supported (based on Android
 * version).
 */
enum class HealthConnectAvailability {
    INSTALLED,
    NOT_INSTALLED,
    NOT_SUPPORTED
}

/**
 * Demonstrates reading and writing from Health Connect.
 */
class HealthConnectManager(private val healthConnectClient: HealthConnectClient,
                           private val context: Context)
{
    //contains one values of HealthConnectAvailability
    var availability = mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)

    init
    {
        checkAvailability()
    }

    private fun checkAvailability()
    {
        availability.value = when
        {
            HealthConnectClient.isAvailable(context) -> HealthConnectAvailability.INSTALLED
            isSupported() -> HealthConnectAvailability.NOT_INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    private fun isSupported() = Build.VERSION.SDK_INT >= MIN_SUPPORTED_SDK

    /**
     * Determines whether all the specified permissions are already granted. It is recommended to
     * call [PermissionController.getGrantedPermissions] first in the permissions flow, as if the
     * permissions are already granted then there is no need to request permissions via
     * [PermissionController.createRequestPermissionResultContract].
     */
    suspend fun hasAllPermissions(permissions: Set<HealthPermission>): Boolean =
        permissions == healthConnectClient.permissionController.getGrantedPermissions(permissions)


}

