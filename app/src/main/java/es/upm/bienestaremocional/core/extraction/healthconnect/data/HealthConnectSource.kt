package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.Record
import java.time.ZonedDateTime

/**
 * Provides default implementation for some functions of [HealthConnectSourceInterface]
 */
abstract class HealthConnectSource(private val healthConnectClient: HealthConnectClient,
                                   private val healthConnectManager: HealthConnectManagerInterface):
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
    {
        return readSource(startTime.toInstant(), endTime.toInstant())
    }

    override suspend fun writePermissionsCheck(): Boolean =
        healthConnectManager.hasAllPermissions(writePermissions)

    override suspend fun writeSource(data: List<Record>)
    {
        healthConnectClient.insertRecords(data)
    }
}