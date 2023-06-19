package es.upm.bienestaremocional.data.healthconnect

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.Record
import java.time.Instant
import java.time.ZonedDateTime

/**
 * Primitives of Health Connect
 */
abstract class HealthConnectSource<T : Record>(
    private val healthConnectClient: HealthConnectClient
) {
    /**
     * Set that contains permissions needed to read data
     */
    abstract val readPermissions: Set<String>

    /**
     * Set that contains permissions needed to write data
     */
    abstract val writePermissions: Set<String>

    /**
     * Checks if all permissions needed for read are granted
     * @see readPermissions
     */
    suspend fun readPermissionsCheck(): Boolean =
        healthConnectClient.hasAllPermissions(readPermissions)

    /**
     * Reads data from the last 30 days until now
     * @return [List] of [T] with the data
     */
    suspend fun readSource(): List<T> {
        val end: ZonedDateTime = ZonedDateTime.now()
        val start: ZonedDateTime = end.minusDays(30)
        return readSource(start, end)
    }

    /**
     * Reads data from source between [startTime] and [endTime]
     * @param startTime: [ZonedDateTime] where read starts
     * @param endTime: [ZonedDateTime] where read ends
     * @return [List] of [T] with the data
     * @see readSource
     */
    suspend fun readSource(startTime: ZonedDateTime, endTime: ZonedDateTime): List<T> =
        readSource(startTime.toInstant(), endTime.toInstant())

    /**
     * Reads data between [startTime] and [endTime]
     * @param startTime: [Instant] where read starts
     * @param endTime: [Instant] where read ends
     * @return [List] of [T] with the data
     * @see readSource
     */
    abstract suspend fun readSource(startTime: Instant, endTime: Instant): List<T>

    /**
     * Checks if all permissions needed for read are granted
     * @see readPermissions
     */
    suspend fun writePermissionsCheck(): Boolean =
        healthConnectClient.hasAllPermissions(writePermissions)

    /**
     * Write data into health connect
     * @param data: [List] of [T] with the data
     */
    suspend fun writeSource(data: List<Record>) {
        healthConnectClient.insertRecords(data)
    }

    private suspend fun HealthConnectClient.hasAllPermissions(permissions: Set<String>): Boolean {
        val granted = this.permissionController.getGrantedPermissions()
        return granted.containsAll(permissions)
    }
}