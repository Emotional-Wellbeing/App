package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.MealType
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.RelationToMeal
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.BloodGlucose
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

/**
 * Implementation of BloodGlucose datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class BloodGlucose(private val healthConnectClient: HealthConnectClient,
                   private val healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<BloodGlucoseRecord>
        {
            // Make yesterday the last day of the hr data
            val lastDay = ZonedDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS)

            return List(5)
            { index ->
                val measureTime = lastDay.minusDays(index.toLong())
                    .withHour(Random.nextInt(0, 24))
                    .withMinute(Random.nextInt(0, 60))
                    .withSecond(Random.nextInt(0, 60))

                val level = BloodGlucose.millimolesPerLiter(Random.nextDouble(0.1,49.9))

                val specimenSource by lazy {
                        when(index % 6)
                        {
                            0 -> BloodGlucoseRecord.SpecimenSource.INTERSTITIAL_FLUID
                            1 -> BloodGlucoseRecord.SpecimenSource.CAPILLARY_BLOOD
                            2 -> BloodGlucoseRecord.SpecimenSource.PLASMA
                            3 -> BloodGlucoseRecord.SpecimenSource.SERUM
                            4 -> BloodGlucoseRecord.SpecimenSource.TEARS
                            else -> BloodGlucoseRecord.SpecimenSource.WHOLE_BLOOD
                        }
                    }

                val mealType by lazy {
                    when(index % 5)
                    {
                        0 -> MealType.BREAKFAST
                        1 -> MealType.DINNER
                        2 -> MealType.LUNCH
                        3 -> MealType.SNACK
                        else -> MealType.UNKNOWN
                    }
                }

                val relationToMeal by lazy {
                    when(index % 4)
                    {
                        0 -> RelationToMeal.AFTER_MEAL
                        1 -> RelationToMeal.BEFORE_MEAL
                        2 -> RelationToMeal.FASTING
                        else -> RelationToMeal.GENERAL
                    }
                }

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

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<Record>
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