package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Power
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of BasalMetabolicRate datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class BasalMetabolicRate(private val healthConnectClient: HealthConnectClient,
                         private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<BasalMetabolicRateRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<BasalMetabolicRateRecord>
        {
            return List(5)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())
                val bmr = Power.kilocaloriesPerDay(Random.nextDouble(1000.0,3000.0))
                BasalMetabolicRateRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    basalMetabolicRate = bmr
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(BasalMetabolicRateRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(BasalMetabolicRateRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<BasalMetabolicRateRecord>
    {
        val request = ReadRecordsRequest(
            recordType = BasalMetabolicRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}