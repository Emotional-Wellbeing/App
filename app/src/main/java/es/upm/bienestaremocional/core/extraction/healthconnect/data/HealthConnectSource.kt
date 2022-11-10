package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.permission.HealthPermission
import java.time.Instant
import java.time.ZonedDateTime

abstract class HealthConnectSource(protected val healthConnectManager: HealthConnectManager)
{
    abstract val permissions : Set<HealthPermission>

    suspend fun permissionsCheck(): Boolean = healthConnectManager.hasAllPermissions(permissions)

    /**
     * Reads data from the last 7 seven days (now - 7 days, now)
     */
    suspend fun readSource(): List<HealthConnectDataClass>
    {
        val lastDay: ZonedDateTime = ZonedDateTime.now()
        val firstDay: ZonedDateTime = lastDay.minusDays(7)
        return readSource(firstDay,lastDay)
    }

    suspend fun readSource(startTime: ZonedDateTime, endTime: ZonedDateTime):
            List<HealthConnectDataClass> = readSource(startTime.toInstant(), endTime.toInstant())

    abstract suspend fun readSource(startTime: Instant, endTime: Instant): List<HealthConnectDataClass>

}