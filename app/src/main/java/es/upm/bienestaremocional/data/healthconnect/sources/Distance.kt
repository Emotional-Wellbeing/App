package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Length
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of Distance datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */


class Distance @Inject constructor(
    private val healthConnectClient: HealthConnectClient
) : HealthConnectSource<DistanceRecord>(healthConnectClient) {
    companion object {
        /**
         * Make demo data
         */
        fun generateDummyData(): List<DistanceRecord> {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())

                val distance = Length.kilometers(Random.nextDouble(0.0, 10.0))

                DistanceRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    distance = distance
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(DistanceRecord::class)
    )

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(DistanceRecord::class)
    )

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<DistanceRecord> {
        val request = ReadRecordsRequest(
            recordType = DistanceRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}