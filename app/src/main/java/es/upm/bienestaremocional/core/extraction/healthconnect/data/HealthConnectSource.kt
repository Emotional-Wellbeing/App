package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.records.Record
import java.time.ZonedDateTime

/**
 * Override some common functions of [HealthConnectSourceInterface]
 */
abstract class HealthConnectSource(private val healthConnectManager: HealthConnectManager):
    HealthConnectSourceInterface
{
    override suspend fun readPermissionsCheck(): Boolean =
        healthConnectManager.hasAllPermissions(readPermissions)

    override suspend fun readSource(): List<Record>
    {
        val lastDay: ZonedDateTime = ZonedDateTime.now()
        val firstDay: ZonedDateTime = lastDay.minusDays(30)
        return readSource(firstDay,lastDay)
    }

    override suspend fun readSource(startTime: ZonedDateTime, endTime: ZonedDateTime): List<Record>
        = readSource(startTime.toInstant(), endTime.toInstant())
}