package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Mass
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.utils.generateTime
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of Weight datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */
class Weight @Inject constructor(
    private val healthConnectClient: HealthConnectClient
) : HealthConnectSource<WeightRecord>(healthConnectClient) {
    companion object {
        /**
         * Make demo data
         */
        fun generateDummyData(): List<WeightRecord> {
            return List(5)
            { index ->
                val time = generateTime(offsetDays = index.toLong() + 1)

                val weight = Mass.kilograms(Random.nextDouble(0.0, 200.0))

                WeightRecord(
                    time = time.toInstant(),
                    zoneOffset = time.offset,
                    weight = weight
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(WeightRecord::class)
    )

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<WeightRecord> {
        val request = ReadRecordsRequest(
            recordType = WeightRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(WeightRecord::class)
    )
}