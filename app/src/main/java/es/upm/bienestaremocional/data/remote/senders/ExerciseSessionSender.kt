package es.upm.bienestaremocional.data.remote.senders

import androidx.health.connect.client.records.ExerciseSessionRecord
import es.upm.bienestaremocional.utils.obtainTimestamp

/**
 * Object used to send [ExerciseSessionRecord], usually through JSON files.
 */
data class ExerciseSessionSender(
    val startTime: Long,
    val endTime: Long,
    val title : String?,
    val notes : String?,
    val exerciseType: String
)
{
    companion object
    {
        fun ExerciseSessionRecord.toSender(): ExerciseSessionSender
        {
            val startTime = obtainTimestamp(startTime, startZoneOffset)
            val endTime = obtainTimestamp(endTime, endZoneOffset)
            val exerciseType = when(exerciseType)
            {
                ExerciseSessionRecord.EXERCISE_TYPE_OTHER_WORKOUT -> "other"
                ExerciseSessionRecord.EXERCISE_TYPE_BADMINTON -> "badminton"
                ExerciseSessionRecord.EXERCISE_TYPE_BASEBALL -> "baseball"
                ExerciseSessionRecord.EXERCISE_TYPE_BASKETBALL -> "basketball"
                ExerciseSessionRecord.EXERCISE_TYPE_BIKING -> "biking"
                ExerciseSessionRecord.EXERCISE_TYPE_BIKING_STATIONARY -> "biking_stationary"
                ExerciseSessionRecord.EXERCISE_TYPE_BOOT_CAMP -> "boot_camp"
                ExerciseSessionRecord.EXERCISE_TYPE_BOXING -> "boxing"
                ExerciseSessionRecord.EXERCISE_TYPE_CALISTHENICS -> "calisthenics"
                ExerciseSessionRecord.EXERCISE_TYPE_CRICKET -> "cricket"
                ExerciseSessionRecord.EXERCISE_TYPE_DANCING -> "dancing"
                ExerciseSessionRecord.EXERCISE_TYPE_ELLIPTICAL -> "elliptical"
                ExerciseSessionRecord.EXERCISE_TYPE_EXERCISE_CLASS -> "exercise_class"
                ExerciseSessionRecord.EXERCISE_TYPE_FENCING -> "fencing"
                ExerciseSessionRecord.EXERCISE_TYPE_FOOTBALL_AMERICAN -> "football_american"
                ExerciseSessionRecord.EXERCISE_TYPE_FOOTBALL_AUSTRALIAN -> "football_australian"
                ExerciseSessionRecord.EXERCISE_TYPE_FRISBEE_DISC -> "frisbee_disc"
                ExerciseSessionRecord.EXERCISE_TYPE_GOLF -> "golf"
                ExerciseSessionRecord.EXERCISE_TYPE_GUIDED_BREATHING -> "guided_breathing"
                ExerciseSessionRecord.EXERCISE_TYPE_GYMNASTICS -> "gymnastics"
                ExerciseSessionRecord.EXERCISE_TYPE_HANDBALL -> "handball"
                ExerciseSessionRecord.EXERCISE_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING -> "high_intensity_interval_training"
                ExerciseSessionRecord.EXERCISE_TYPE_HIKING -> "hiking"
                ExerciseSessionRecord.EXERCISE_TYPE_ICE_HOCKEY -> "ice_hockey"
                ExerciseSessionRecord.EXERCISE_TYPE_ICE_SKATING -> "ice_skating"
                ExerciseSessionRecord.EXERCISE_TYPE_MARTIAL_ARTS -> "martial_arts"
                ExerciseSessionRecord.EXERCISE_TYPE_PADDLING -> "paddling"
                ExerciseSessionRecord.EXERCISE_TYPE_PARAGLIDING -> "paragliding"
                ExerciseSessionRecord.EXERCISE_TYPE_PILATES -> "pilates"
                ExerciseSessionRecord.EXERCISE_TYPE_RACQUETBALL -> "racquetball"
                ExerciseSessionRecord.EXERCISE_TYPE_ROCK_CLIMBING -> "rock_climbing"
                ExerciseSessionRecord.EXERCISE_TYPE_ROLLER_HOCKEY -> "roller_hockey"
                ExerciseSessionRecord.EXERCISE_TYPE_ROWING -> "rowing"
                ExerciseSessionRecord.EXERCISE_TYPE_ROWING_MACHINE -> "rowing_machine"
                ExerciseSessionRecord.EXERCISE_TYPE_RUGBY -> "rugby"
                ExerciseSessionRecord.EXERCISE_TYPE_RUNNING -> "running"
                ExerciseSessionRecord.EXERCISE_TYPE_RUNNING_TREADMILL -> "running_treadmill"
                ExerciseSessionRecord.EXERCISE_TYPE_SAILING -> "sailing"
                ExerciseSessionRecord.EXERCISE_TYPE_SCUBA_DIVING -> "scuba_diving"
                ExerciseSessionRecord.EXERCISE_TYPE_SKATING -> "skating"
                ExerciseSessionRecord.EXERCISE_TYPE_SKIING -> "skiing"
                ExerciseSessionRecord.EXERCISE_TYPE_SNOWBOARDING -> "snowboarding"
                ExerciseSessionRecord.EXERCISE_TYPE_SNOWSHOEING -> "snowshoeing"
                ExerciseSessionRecord.EXERCISE_TYPE_SOCCER -> "soccer"
                ExerciseSessionRecord.EXERCISE_TYPE_SOFTBALL -> "softball"
                ExerciseSessionRecord.EXERCISE_TYPE_SQUASH -> "squash"
                ExerciseSessionRecord.EXERCISE_TYPE_STAIR_CLIMBING -> "stair_climbing"
                ExerciseSessionRecord.EXERCISE_TYPE_STAIR_CLIMBING_MACHINE -> "stair_climbing_machine"
                ExerciseSessionRecord.EXERCISE_TYPE_STRENGTH_TRAINING -> "strength_training"
                ExerciseSessionRecord.EXERCISE_TYPE_STRETCHING -> "stretching"
                ExerciseSessionRecord.EXERCISE_TYPE_SURFING -> "surfing"
                ExerciseSessionRecord.EXERCISE_TYPE_SWIMMING_OPEN_WATER -> "swimming_open_water"
                ExerciseSessionRecord.EXERCISE_TYPE_SWIMMING_POOL -> "swimming_pool"
                ExerciseSessionRecord.EXERCISE_TYPE_TABLE_TENNIS -> "table_tennis"
                ExerciseSessionRecord.EXERCISE_TYPE_TENNIS -> "tennis"
                ExerciseSessionRecord.EXERCISE_TYPE_VOLLEYBALL -> "volleyball"
                ExerciseSessionRecord.EXERCISE_TYPE_WALKING -> "walking"
                ExerciseSessionRecord.EXERCISE_TYPE_WATER_POLO -> "water_polo"
                ExerciseSessionRecord.EXERCISE_TYPE_WEIGHTLIFTING -> "weightlifting"
                ExerciseSessionRecord.EXERCISE_TYPE_WHEELCHAIR -> "wheelchair"
                ExerciseSessionRecord.EXERCISE_TYPE_YOGA -> "yoga"
                else -> "unknown"
            }
            
            return ExerciseSessionSender(
                startTime = startTime,
                endTime = endTime,
                title = title,
                notes = notes,
                exerciseType = exerciseType,
            )
        }
    }
}
