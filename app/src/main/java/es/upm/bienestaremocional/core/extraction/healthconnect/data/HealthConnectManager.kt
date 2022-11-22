package es.upm.bienestaremocional.core.extraction.healthconnect.data

import android.content.Context
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission

/**
 * Demonstrates reading and writing from Health Connect.
 */
class HealthConnectManager(private val healthConnectClient: HealthConnectClient,
                           private val context: Context) : HealthConnectManagerInterface
{
    //contains one values of HealthConnectAvailability
    override var availability = mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)

    init
    {
        checkAvailability()
    }

    override fun isSupported() : Boolean = Build.VERSION.SDK_INT >= MIN_SUPPORTED_SDK

    override fun checkAvailability()
    {
        availability.value = when
        {
            HealthConnectClient.isAvailable(context) -> HealthConnectAvailability.INSTALLED
            isSupported() -> HealthConnectAvailability.NOT_INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    override suspend fun hasAllPermissions(permissions: Set<HealthPermission>): Boolean =
        permissions == healthConnectClient.permissionController.getGrantedPermissions(permissions)

}

