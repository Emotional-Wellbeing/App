package es.upm.bienestaremocional.core.extraction.healthconnect.data

import androidx.health.connect.client.records.Record
import java.time.Instant
import java.time.ZonedDateTime

/**
 * Interface that contains healthconnect's read primitives
 */
interface HealthConnectSourceInterface<T: Record>
{
    /**
     * Set that contains permissions needed to read data
     */
    val readPermissions : Set<String>

    /**
     * Checks if all permissions needed for read are granted
     * @see readPermissions
     */
    suspend fun readPermissionsCheck(): Boolean

    /**
     * Reads data from the last 30 days until now
     * @return [List] of [T] with the data
     */
    suspend fun readSource(): List<T>

    /**
     * Reads data from source between [startTime] and [endTime]
     * @param startTime: [ZonedDateTime] where read starts
     * @param endTime: [ZonedDateTime] where read ends
     * @return [List] of [T] with the data
     * @see readSource
     */
    suspend fun readSource(startTime: ZonedDateTime, endTime: ZonedDateTime): List<T>

    /**
     * Reads data between [startTime] and [endTime]
     * @param startTime: [Instant] where read starts
     * @param endTime: [Instant] where read ends
     * @return [List] of [T] with the data
     * @see readSource
     */
    suspend fun readSource(startTime: Instant, endTime: Instant): List<T>

    /**
     * Set that contains permissions needed to write data
     */
    val writePermissions : Set<String>

    /**
     * Checks if all permissions needed for read are granted
     * @see readPermissions
     */
    suspend fun writePermissionsCheck(): Boolean

    /**
     * Write data into health connect
     * @param data: [List] of [T] with the data
     */
    suspend fun writeSource(data: List<Record>)

}