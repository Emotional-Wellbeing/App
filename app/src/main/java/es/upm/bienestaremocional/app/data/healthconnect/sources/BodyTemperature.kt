package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Temperature
import es.upm.bienestaremocional.app.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of BodyTemperature datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class BodyTemperature(private val healthConnectClient: HealthConnectClient,
                      private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<BodyTemperatureRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<BodyTemperatureRecord>
        {

            return List(10)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())

                val temperature = Temperature.celsius(Random.nextDouble(35.0,40.0))
                val measureLocation by lazy {
                    when(index % 10)
                    {
                        0 -> BodyTemperatureMeasurementLocation.ARMPIT
                        1 -> BodyTemperatureMeasurementLocation.FINGER
                        2 -> BodyTemperatureMeasurementLocation.FOREHEAD
                        3 -> BodyTemperatureMeasurementLocation.MOUTH
                        4 -> BodyTemperatureMeasurementLocation.RECTUM
                        5 -> BodyTemperatureMeasurementLocation.TEMPORAL_ARTERY
                        6 -> BodyTemperatureMeasurementLocation.TOE
                        7 -> BodyTemperatureMeasurementLocation.EAR
                        8 -> BodyTemperatureMeasurementLocation.WRIST
                        else -> BodyTemperatureMeasurementLocation.VAGINA
                    }
                }
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
        HealthPermission.createReadPermission(BodyTemperatureRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(BodyTemperatureRecord::class))

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