package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Mass
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of Weight datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */
class Weight @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
):HealthConnectSource<WeightRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<WeightRecord>
        {
            return List(5)
            { index ->
                val time = generateTime(offsetDays = index.toLong())

                val weight = Mass.kilograms(Random.nextDouble(0.0,200.0))

                WeightRecord(
                    time = time.toInstant(),
                    zoneOffset = time.offset,
                    weight = weight
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(WeightRecord::class))

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(WeightRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<WeightRecord>
    {
        val request = ReadRecordsRequest(
            recordType = WeightRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}