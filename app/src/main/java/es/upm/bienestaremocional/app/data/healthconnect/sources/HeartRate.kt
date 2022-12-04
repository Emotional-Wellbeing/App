package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.utils.generateInterval
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.linspace
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of HeartRate datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */
class HeartRate(private val healthConnectClient: HealthConnectClient,
                private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<HeartRateRecord>(healthConnectClient,healthConnectManager)
{

    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<HeartRateRecord>
        {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())

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

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(HeartRateRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<HeartRateRecord>
    {
        val request = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}