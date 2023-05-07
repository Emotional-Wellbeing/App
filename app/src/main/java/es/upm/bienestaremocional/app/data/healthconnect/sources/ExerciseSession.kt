package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.utils.generateInterval
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Instant
import javax.inject.Inject

/**
 * Implementation of ExerciseSession datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */


class ExerciseSession @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManagerInterface
):HealthConnectSource<ExerciseSessionRecord>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Make demo data
         */
        fun generateDummyData() : List<ExerciseSessionRecord>
        {
            val differentExercises = 61
            return List(differentExercises)
            { index ->
                val (init, end) = generateInterval(offsetDays = index.toLong())

                val exerciseType = when(index)
                {
                    0 -> ExerciseSessionRecord.EXERCISE_TYPE_OTHER_WORKOUT
                    1 -> ExerciseSessionRecord.EXERCISE_TYPE_BADMINTON
                    2 -> ExerciseSessionRecord.EXERCISE_TYPE_BASEBALL
                    3 -> ExerciseSessionRecord.EXERCISE_TYPE_BASKETBALL
                    4 -> ExerciseSessionRecord.EXERCISE_TYPE_BIKING
                    5 -> ExerciseSessionRecord.EXERCISE_TYPE_BIKING_STATIONARY
                    6 -> ExerciseSessionRecord.EXERCISE_TYPE_BOOT_CAMP
                    7 -> ExerciseSessionRecord.EXERCISE_TYPE_BOXING
                    8 -> ExerciseSessionRecord.EXERCISE_TYPE_CALISTHENICS
                    9 -> ExerciseSessionRecord.EXERCISE_TYPE_CRICKET
                    10 -> ExerciseSessionRecord.EXERCISE_TYPE_DANCING
                    11 -> ExerciseSessionRecord.EXERCISE_TYPE_ELLIPTICAL
                    12 -> ExerciseSessionRecord.EXERCISE_TYPE_EXERCISE_CLASS
                    13 -> ExerciseSessionRecord.EXERCISE_TYPE_FENCING
                    14 -> ExerciseSessionRecord.EXERCISE_TYPE_FOOTBALL_AMERICAN
                    15 -> ExerciseSessionRecord.EXERCISE_TYPE_FOOTBALL_AUSTRALIAN
                    16 -> ExerciseSessionRecord.EXERCISE_TYPE_FRISBEE_DISC
                    17 -> ExerciseSessionRecord.EXERCISE_TYPE_GOLF
                    18 -> ExerciseSessionRecord.EXERCISE_TYPE_GUIDED_BREATHING
                    19 -> ExerciseSessionRecord.EXERCISE_TYPE_GYMNASTICS
                    20 -> ExerciseSessionRecord.EXERCISE_TYPE_HANDBALL
                    21 -> ExerciseSessionRecord.EXERCISE_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING
                    22 -> ExerciseSessionRecord.EXERCISE_TYPE_HIKING
                    23 -> ExerciseSessionRecord.EXERCISE_TYPE_ICE_HOCKEY
                    24 -> ExerciseSessionRecord.EXERCISE_TYPE_ICE_SKATING
                    25 -> ExerciseSessionRecord.EXERCISE_TYPE_MARTIAL_ARTS
                    26 -> ExerciseSessionRecord.EXERCISE_TYPE_PADDLING
                    27 -> ExerciseSessionRecord.EXERCISE_TYPE_PARAGLIDING
                    28 -> ExerciseSessionRecord.EXERCISE_TYPE_PILATES
                    29 -> ExerciseSessionRecord.EXERCISE_TYPE_RACQUETBALL
                    30 -> ExerciseSessionRecord.EXERCISE_TYPE_ROCK_CLIMBING
                    31 -> ExerciseSessionRecord.EXERCISE_TYPE_ROLLER_HOCKEY
                    32 -> ExerciseSessionRecord.EXERCISE_TYPE_ROWING
                    33 -> ExerciseSessionRecord.EXERCISE_TYPE_ROWING_MACHINE
                    34 -> ExerciseSessionRecord.EXERCISE_TYPE_RUGBY
                    35 -> ExerciseSessionRecord.EXERCISE_TYPE_RUNNING
                    36 -> ExerciseSessionRecord.EXERCISE_TYPE_RUNNING_TREADMILL
                    37 -> ExerciseSessionRecord.EXERCISE_TYPE_SAILING
                    38 -> ExerciseSessionRecord.EXERCISE_TYPE_SCUBA_DIVING
                    39 -> ExerciseSessionRecord.EXERCISE_TYPE_SKATING
                    40 -> ExerciseSessionRecord.EXERCISE_TYPE_SKIING
                    41 -> ExerciseSessionRecord.EXERCISE_TYPE_SNOWBOARDING
                    42 -> ExerciseSessionRecord.EXERCISE_TYPE_SNOWSHOEING
                    43 -> ExerciseSessionRecord.EXERCISE_TYPE_SOCCER
                    44 -> ExerciseSessionRecord.EXERCISE_TYPE_SOFTBALL
                    45 -> ExerciseSessionRecord.EXERCISE_TYPE_SQUASH
                    46 -> ExerciseSessionRecord.EXERCISE_TYPE_STAIR_CLIMBING
                    47 -> ExerciseSessionRecord.EXERCISE_TYPE_STAIR_CLIMBING_MACHINE
                    48 -> ExerciseSessionRecord.EXERCISE_TYPE_STRENGTH_TRAINING
                    49 -> ExerciseSessionRecord.EXERCISE_TYPE_STRETCHING
                    50 -> ExerciseSessionRecord.EXERCISE_TYPE_SURFING
                    51 -> ExerciseSessionRecord.EXERCISE_TYPE_SWIMMING_OPEN_WATER
                    52 -> ExerciseSessionRecord.EXERCISE_TYPE_SWIMMING_POOL
                    53 -> ExerciseSessionRecord.EXERCISE_TYPE_TABLE_TENNIS
                    54 -> ExerciseSessionRecord.EXERCISE_TYPE_TENNIS
                    55 -> ExerciseSessionRecord.EXERCISE_TYPE_VOLLEYBALL
                    56 -> ExerciseSessionRecord.EXERCISE_TYPE_WALKING
                    57 -> ExerciseSessionRecord.EXERCISE_TYPE_WATER_POLO
                    58 -> ExerciseSessionRecord.EXERCISE_TYPE_WEIGHTLIFTING
                    59 -> ExerciseSessionRecord.EXERCISE_TYPE_WHEELCHAIR
                    60 -> ExerciseSessionRecord.EXERCISE_TYPE_YOGA
                    else -> ExerciseSessionRecord.EXERCISE_TYPE_OTHER_WORKOUT
                }

                ExerciseSessionRecord(
                    startTime = init.toInstant(),
                    startZoneOffset = init.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    exerciseType = exerciseType,
                    title = "Exercise",
                    notes = "Notes"
                )
            }
        }
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(ExerciseSessionRecord::class))

    override val writePermissions = setOf(
        HealthPermission.getWritePermission(ExerciseSessionRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<ExerciseSessionRecord>
    {
        val request = ReadRecordsRequest(
            recordType = ExerciseSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}