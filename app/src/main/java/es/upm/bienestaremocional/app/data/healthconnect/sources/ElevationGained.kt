package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Length
import es.upm.bienestaremocional.app.generateInterval
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of ElevationGained datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class ElevationGained(private val healthConnectClient: HealthConnectClient,
                      private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<ElevationGainedRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<ElevationGainedRecord>
        {
            return List(5)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())

                val elevation = Length.meters(Random.nextDouble(0.0,2500.0))

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
        HealthPermission.createReadPermission(ElevationGainedRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(ElevationGainedRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<ElevationGainedRecord>
    {
        val request = ReadRecordsRequest(
            recordType = ElevationGainedRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}