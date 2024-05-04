package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Length
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of ElevationGained datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */


class ElevationGained @Inject constructor(
    private val healthConnectClient: HealthConnectClient
) : HealthConnectSource<ElevationGainedRecord>(healthConnectClient) {
    companion object {
        /**
         * Make demo data
         */
        fun generateDummyData(): List<ElevationGainedRecord> {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong() + 1)

                val elevation = Length.meters(Random.nextDouble(0.0, 2500.0))

                ElevationGainedRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    elevation = elevation
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(ElevationGainedRecord::class)
    )

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(ElevationGainedRecord::class)
    )

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<ElevationGainedRecord> {
        val request = ReadRecordsRequest(
            recordType = ElevationGainedRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}