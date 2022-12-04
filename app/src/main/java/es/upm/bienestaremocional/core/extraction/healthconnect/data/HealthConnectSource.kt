package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.Record
import java.time.ZonedDateTime

/**
 * Provides default implementation for some functions of [HealthConnectSourceInterface]
 */
abstract class HealthConnectSource<T: Record>(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
): HealthConnectSourceInterface<T>
{

    override suspend fun readPermissionsCheck(): Boolean =
        healthConnectManager.hasAllPermissions(readPermissions)


    override suspend fun readSource(): List<T>
    {
        val lastDay: ZonedDateTime = ZonedDateTime.now()
        val firstDay: ZonedDateTime = lastDay.minusDays(30)
        return readSource(firstDay,lastDay)
    }

    override suspend fun readSource(startTime: ZonedDateTime, endTime: ZonedDateTime): List<T> =
        readSource(startTime.toInstant(), endTime.toInstant())

    override suspend fun writePermissionsCheck(): Boolean =
        healthConnectManager.hasAllPermissions(writePermissions)

    override suspend fun writeSource(data: List<Record>)
    {
        healthConnectClient.insertRecords(data)
    }
}