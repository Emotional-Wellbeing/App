package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Temperature
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of BodyTemperature datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class BodyTemperature @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
): HealthConnectSource<BodyTemperatureRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<BodyTemperatureRecord>
        {

            return List(11)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())

                val temperature = Temperature.celsius(Random.nextDouble(35.0,40.0))
                val measureLocation = index % 11
                BodyTemperatureRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    temperature = temperature,
                    measurementLocation = measureLocation
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(BodyTemperatureRecord::class))

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(BodyTemperatureRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<BodyTemperatureRecord>
    {
        val request = ReadRecordsRequest(
            recordType = BodyTemperatureRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}