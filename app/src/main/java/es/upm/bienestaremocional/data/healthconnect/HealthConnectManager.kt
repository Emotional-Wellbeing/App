package es.upm.bienestaremocional.data.healthconnect

import androidx.compose.runtime.MutableState

/**
 * Interface with the Health Connect operations that are related to permissions
 * and availability
 */
interface HealthConnectManager
{
    var availability: MutableState<HealthConnectAvailability>

    /**
     * Check if Health Connect is installed or could be
     */
    fun checkAvailability()

    /**
     * Check if [permissions] are granted by user
     */
    suspend fun hasAllPermissions(permissions: Set<String>): Boolean
}