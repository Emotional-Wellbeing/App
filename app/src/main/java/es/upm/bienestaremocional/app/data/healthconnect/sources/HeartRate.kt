package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.linspace
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

/**
 * Implementation of HeartRate datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */
class HeartRate(private val healthConnectClient: HealthConnectClient,
                private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource(healthConnectManager)
{

    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<HeartRateRecord>
        {
            // Make yesterday the last day of the hr data
            val lastDay = ZonedDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS)

            return List(5)
            { index ->
                val init = lastDay.minusDays(index.toLong())
                    .withHour(Random.nextInt(0, 11))
                    .withMinute(Random.nextInt(0, 60))
                val end = lastDay.minusDays(index.toLong())
                    .withHour(Random.nextInt(12, 23))
                    .withMinute(Random.nextInt(0, 60))
                val numberSamples = 5
                val samples = linspace(
                    init.toInstant().epochSecond,
                    end.toInstant().epochSecond,
                    numberSamples
                )
                    .map { instant ->
                        HeartRateRecord.Sample(
                            Instant.ofEpochSecond(instant),
                            Random.nextLong(60, 190)
                        )
                    }
                HeartRateRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    samples = samples
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(HeartRateRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<Record>
    {
        val sessions = mutableListOf<HeartRateRecord>()

        val hearthRateRequest = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val heartrateItems = healthConnectClient.readRecords(hearthRateRequest)

        heartrateItems.records.forEach { session ->
            sessions.add(
                HeartRateRecord(
                    startTime = session.startTime,
                    startZoneOffset = session.startZoneOffset,
                    endTime = session.endTime,
                    endZoneOffset = session.endZoneOffset,
                    samples = session.samples
                )
            )
        }
        return sessions
    }

    /**
     * Set that contains permissions needed to write data
     */
    val writePermissions = setOf(HealthPermission.createWritePermission(HeartRateRecord::class))

    /**
     * Checks if all permissions needed for read are granted
     * @see writePermissions
     */
    suspend fun writePermissionsCheck(): Boolean =
        healthConnectManager.hasAllPermissions(writePermissions)


    /**
     * Write data into health connect
     */
    suspend fun writeSource(data: List<Record>)
    {
        healthConnectClient.insertRecords(data)
    }

}