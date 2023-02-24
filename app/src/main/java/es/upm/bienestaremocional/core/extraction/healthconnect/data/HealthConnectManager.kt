package es.upm.bienestaremocional.core.extraction.healthconnect.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient

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


    override fun checkAvailability()
    {
        availability.value = when
        {
            HealthConnectClient.isProviderAvailable(context) -> HealthConnectAvailability.INSTALLED
            HealthConnectClient.isApiSupported() -> HealthConnectAvailability.NOT_INSTALLED
            else -> HealthConnectAvailability.NOT_SUPPORTED
        }
    }

    override suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        return granted.containsAll(permissions)
    }
}

