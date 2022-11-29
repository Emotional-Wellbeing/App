package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

/**
 * Implementation of Steps datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class Steps(private val healthConnectClient: HealthConnectClient,
            private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<StepsRecord>
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
                val count = Random.nextLong(0,20000)
                StepsRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    count = count
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(StepsRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<Record>
    {
        val stepsRequest = ReadRecordsRequest(
            recordType = StepsRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val stepsItems = healthConnectClient.readRecords(stepsRequest)

        return stepsItems.records
    }

    /**
     * Set that contains permissions needed to write data
     */
    override val writePermissions = setOf(
        HealthPermission.createWritePermission(StepsRecord::class))

}