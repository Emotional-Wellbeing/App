package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of RespiratoryRate datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class RespiratoryRate(private val healthConnectClient: HealthConnectClient,
                      private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<RespiratoryRateRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<RespiratoryRateRecord>
        {
            return List(5)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())

                val rate = Random.nextDouble(0.0,100.0)
                RespiratoryRateRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    rate = rate
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(RespiratoryRateRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(RespiratoryRateRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<RespiratoryRateRecord>
    {
        val request = ReadRecordsRequest(
            recordType = RespiratoryRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}