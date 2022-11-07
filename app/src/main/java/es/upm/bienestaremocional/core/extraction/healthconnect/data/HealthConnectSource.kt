package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.permission.HealthPermission

abstract class HealthConnectSource(protected val healthConnectManager: HealthConnectManager)
{
    abstract val permissions : Set<HealthPermission>

    suspend fun permissionsCheck(): Boolean = healthConnectManager.hasAllPermissions(permissions)

    abstract suspend fun readSource(): List<HealthConnectDataClass>
}