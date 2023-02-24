package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.compose.runtime.MutableState

/**
 * Interface with the Health Connect operations that are related to permissions
 * and availability
 */
interface HealthConnectManagerInterface
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