package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.utils.generateInterval
import es.upm.bienestaremocional.utils.linspace
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of HeartRate datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */
class HeartRate @Inject constructor(
    private val healthConnectClient: HealthConnectClient
) : HealthConnectSource<HeartRateRecord>(healthConnectClient) {

    companion object {
        /**
         * Make demo data
         */
        fun generateDummyData(): List<HeartRateRecord> {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong() + 1)

                val numberSamples = 5
                val samples = linspace(
                    init,
                    end,
                    numberSamples
                )
                    .map {
                        HeartRateRecord.Sample(
                            it.toInstant(),
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
        HealthPermission.getReadPermission(HeartRateRecord::class)
    )

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(HeartRateRecord::class)
    )

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<HeartRateRecord> {
        val request = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}