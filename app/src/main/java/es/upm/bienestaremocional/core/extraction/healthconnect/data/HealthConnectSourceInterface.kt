package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import java.time.Instant
import java.time.ZonedDateTime

/**
 * Interface used by all health connect sources
 */
interface HealthConnectSourceInterface
{
    val readPermissions : Set<HealthPermission>

    suspend fun readPermissionsCheck(): Boolean

    /**
     * Reads data from the last 30 days
     */
    suspend fun readSource(): List<Record>

    suspend fun readSource(startTime: ZonedDateTime, endTime: ZonedDateTime):
            List<Record>

    suspend fun readSource(startTime: Instant, endTime: Instant): List<Record>
}