package es.upm.bienestaremocional.data.healthconnect

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient

/**
 * Demonstrates reading and writing from Health Connect.
 */
class HealthConnectManagerImpl(private val healthConnectClient: HealthConnectClient,
                               private val context: Context) : HealthConnectManager
{
    //contains one values of HealthConnectAvailability
    override var availability = mutableStateOf(HealthConnectAvailability.NOT_SUPPORTED)

    init
    {
        checkAvailability()
    }


    override fun checkAvailability()
    {
        availability.value = when(HealthConnectClient.sdkStatus(context))
        {
            HealthConnectClient.SDK_UNAVAILABLE -> HealthConnectAvailability.NOT_SUPPORTED
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> HealthConnectAvailability.NOT_INSTALLED
            HealthConnectClient.SDK_AVAILABLE -> HealthConnectAvailability.INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    override suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        return granted.containsAll(permissions)
    }
}

