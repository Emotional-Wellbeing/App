package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.BloodGlucose
import es.upm.bienestaremocional.app.utils.generateTime
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of BloodGlucose datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class BloodGlucose @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
): HealthConnectSource<BloodGlucoseRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<BloodGlucoseRecord>
        {
            return List(7)
            { index ->

                val measureTime = generateTime(offsetDays = index.toLong())

                val level = BloodGlucose.millimolesPerLiter(Random.nextDouble(0.1,49.9))

                val specimenSource = index % 7
                val mealType = index % 5
                val relationToMeal = index % 4

                BloodGlucoseRecord(
                    time = measureTime.toInstant(),
                    zoneOffset = measureTime.offset,
                    level = level,
                    specimenSource = specimenSource,
                    mealType = mealType,
                    relationToMeal = relationToMeal
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.createReadPermission(BloodGlucoseRecord::class))

    override val writePermissions = setOf(
        HealthPermission.createWritePermission(BloodGlucoseRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<BloodGlucoseRecord>
    {
        val request = ReadRecordsRequest(
            recordType = BloodGlucoseRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}