package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.FloorsClimbedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.data.healthconnect.HealthConnectManager
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of FloorsClimbed datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */

class FloorsClimbed @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManager
) : HealthConnectSource<FloorsClimbedRecord>(healthConnectClient, healthConnectManager) {
    companion object {
        /**
         * Make demo data
         */
        fun generateDummyData(): List<FloorsClimbedRecord> {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())

                val floors = Random.nextDouble(0.0, 1000000.0)

                FloorsClimbedRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    floors = floors
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(FloorsClimbedRecord::class)
    )

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(FloorsClimbedRecord::class)
    )

    override suspend fun readSource(
        startTime: Instant,
        endTime: Instant
    ): List<FloorsClimbedRecord> {
        val request = ReadRecordsRequest(
            recordType = FloorsClimbedRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}