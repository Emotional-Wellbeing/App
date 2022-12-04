package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Percentage
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of OxygenSaturation datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class OxygenSaturation(private val healthConnectClient: HealthConnectClient,
                       private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<OxygenSaturationRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<OxygenSaturationRecord>
        {
            return List(5)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())

                val percentage = Percentage(Random.nextDouble(0.0,100.0))
                OxygenSaturationRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    percentage = percentage
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(OxygenSaturationRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(OxygenSaturationRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<OxygenSaturationRecord>
    {
        val request = ReadRecordsRequest(
            recordType = OxygenSaturationRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}