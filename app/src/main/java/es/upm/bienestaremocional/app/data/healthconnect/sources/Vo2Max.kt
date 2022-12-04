package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Vo2MaxRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import kotlin.random.Random

/**
 * Implementation of Vo2Max datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class Vo2Max(private val healthConnectClient: HealthConnectClient,
             private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource<Vo2MaxRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<Vo2MaxRecord>
        {
            return List(6)
            { index ->
                val measureTime = generateTime(offsetDays = index.toLong())
                val vo2 = Random.nextDouble(0.0,100.0)
                val measurementMethod by lazy {
                    when(index % 6)
                    {
                        0 -> Vo2MaxRecord.MeasurementMethod.METABOLIC_CART
                        1 -> Vo2MaxRecord.MeasurementMethod.HEART_RATE_RATIO
                        2 -> Vo2MaxRecord.MeasurementMethod.COOPER_TEST
                        3 -> Vo2MaxRecord.MeasurementMethod.MULTISTAGE_FITNESS_TEST
                        4 -> Vo2MaxRecord.MeasurementMethod.ROCKPORT_FITNESS_TEST
                        else -> Vo2MaxRecord.MeasurementMethod.OTHER
                    }
                }
                Vo2MaxRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    vo2MillilitersPerMinuteKilogram = vo2,
                    measurementMethod = measurementMethod
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(Vo2MaxRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(Vo2MaxRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<Vo2MaxRecord>
    {
        val request = ReadRecordsRequest(
            recordType = Vo2MaxRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}