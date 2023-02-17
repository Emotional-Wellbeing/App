package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.utils.generateInterval
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of Steps datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */

class Steps @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
): HealthConnectSource<StepsRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<StepsRecord>
        {

            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())
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

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(StepsRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<StepsRecord>
    {
        val stepsRequest = ReadRecordsRequest(
            recordType = StepsRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val stepsItems = healthConnectClient.readRecords(stepsRequest)
        return stepsItems.records
    }
}