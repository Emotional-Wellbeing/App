package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of RestingHeartRate datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */
class RestingHeartRate @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
): HealthConnectSource<RestingHeartRateRecord>(healthConnectClient,healthConnectManager)
{

    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<RestingHeartRateRecord>
        {
            return List(5)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())
                val bpm = Random.nextLong(30, 100)
                RestingHeartRateRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    beatsPerMinute = bpm
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(RestingHeartRateRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<RestingHeartRateRecord>
    {
        val request = ReadRecordsRequest(
            recordType = RestingHeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)

        return items.records
    }

    /**
     * Set that contains permissions needed to write data
     */
    override val writePermissions = setOf(
        HealthPermission.getWritePermission(RestingHeartRateRecord::class))

}