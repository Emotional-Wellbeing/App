package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Energy
import es.upm.bienestaremocional.data.healthconnect.HealthConnectManager
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of TotalCaloriesBurned datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class TotalCaloriesBurned @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManager
) : HealthConnectSource<TotalCaloriesBurnedRecord>(healthConnectClient, healthConnectManager) {
    companion object {
        /**
         * Make demo data
         */
        fun generateDummyData(): List<TotalCaloriesBurnedRecord> {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())
                val energy = Energy.kilocalories(Random.nextDouble(1000.0, 5000.0))
                TotalCaloriesBurnedRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    energy = energy
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class)
    )

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class)
    )

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<TotalCaloriesBurnedRecord> {
        val request = ReadRecordsRequest(
            recordType = TotalCaloriesBurnedRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}