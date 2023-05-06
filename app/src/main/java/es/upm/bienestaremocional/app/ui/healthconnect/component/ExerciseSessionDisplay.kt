package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.ExerciseSessionRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.ExerciseSession
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [ExerciseSessionRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun ExerciseSessionRecord.Display(widthSize: WindowWidthSizeClass)
{
    val exerciseFormatted = decodeExerciseType()

    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        title?.let { DrawPair(key = stringResource(id = R.string.title), value = it) }
        notes?.let { DrawPair(key = stringResource(id = R.string.notes), value = it) }
        DrawPair(key = stringResource(id = R.string.exercise), value = exerciseFormatted)

        metadata.Display(widthSize)
    }
}

@Composable
private fun ExerciseSessionRecord.decodeExerciseType(): String =
    stringResource(when(exerciseType)
        {
            ExerciseSessionRecord.EXERCISE_TYPE_OTHER_WORKOUT -> R.string.other
            ExerciseSessionRecord.EXERCISE_TYPE_BADMINTON -> R.string.badminton
            ExerciseSessionRecord.EXERCISE_TYPE_BASEBALL -> R.string.baseball
            ExerciseSessionRecord.EXERCISE_TYPE_BASKETBALL -> R.string.basketball
            ExerciseSessionRecord.EXERCISE_TYPE_BIKING -> R.string.biking
            ExerciseSessionRecord.EXERCISE_TYPE_BIKING_STATIONARY -> R.string.biking_stationary
            ExerciseSessionRecord.EXERCISE_TYPE_BOOT_CAMP -> R.string.boot_camp
            ExerciseSessionRecord.EXERCISE_TYPE_BOXING -> R.string.boxing
            ExerciseSessionRecord.EXERCISE_TYPE_CALISTHENICS -> R.string.calisthenics
            ExerciseSessionRecord.EXERCISE_TYPE_CRICKET -> R.string.cricket
            ExerciseSessionRecord.EXERCISE_TYPE_DANCING -> R.string.dancing
            ExerciseSessionRecord.EXERCISE_TYPE_ELLIPTICAL -> R.string.elliptical
            ExerciseSessionRecord.EXERCISE_TYPE_EXERCISE_CLASS -> R.string.exercise_class
            ExerciseSessionRecord.EXERCISE_TYPE_FENCING -> R.string.fencing
            ExerciseSessionRecord.EXERCISE_TYPE_FOOTBALL_AMERICAN -> R.string.football_american
            ExerciseSessionRecord.EXERCISE_TYPE_FOOTBALL_AUSTRALIAN -> R.string.football_australian
            ExerciseSessionRecord.EXERCISE_TYPE_FRISBEE_DISC -> R.string.frisbee_disc
            ExerciseSessionRecord.EXERCISE_TYPE_GOLF -> R.string.golf
            ExerciseSessionRecord.EXERCISE_TYPE_GUIDED_BREATHING -> R.string.guided_breathing
            ExerciseSessionRecord.EXERCISE_TYPE_GYMNASTICS -> R.string.gymnastics
            ExerciseSessionRecord.EXERCISE_TYPE_HANDBALL -> R.string.handball
            ExerciseSessionRecord.EXERCISE_TYPE_HIGH_INTENSITY_INTERVAL_TRAINING -> R.string.high_intensity_interval_training
            ExerciseSessionRecord.EXERCISE_TYPE_HIKING -> R.string.hiking
            ExerciseSessionRecord.EXERCISE_TYPE_ICE_HOCKEY -> R.string.ice_hockey
            ExerciseSessionRecord.EXERCISE_TYPE_ICE_SKATING -> R.string.ice_skating
            ExerciseSessionRecord.EXERCISE_TYPE_MARTIAL_ARTS -> R.string.martial_arts
            ExerciseSessionRecord.EXERCISE_TYPE_PADDLING -> R.string.paddling
            ExerciseSessionRecord.EXERCISE_TYPE_PARAGLIDING -> R.string.paragliding
            ExerciseSessionRecord.EXERCISE_TYPE_PILATES -> R.string.pilates
            ExerciseSessionRecord.EXERCISE_TYPE_RACQUETBALL -> R.string.racquetball
            ExerciseSessionRecord.EXERCISE_TYPE_ROCK_CLIMBING -> R.string.rock_climbing
            ExerciseSessionRecord.EXERCISE_TYPE_ROLLER_HOCKEY -> R.string.roller_hockey
            ExerciseSessionRecord.EXERCISE_TYPE_ROWING -> R.string.rowing
            ExerciseSessionRecord.EXERCISE_TYPE_ROWING_MACHINE -> R.string.rowing_machine
            ExerciseSessionRecord.EXERCISE_TYPE_RUGBY -> R.string.rugby
            ExerciseSessionRecord.EXERCISE_TYPE_RUNNING -> R.string.running
            ExerciseSessionRecord.EXERCISE_TYPE_RUNNING_TREADMILL -> R.string.running_treadmill
            ExerciseSessionRecord.EXERCISE_TYPE_SAILING -> R.string.sailing
            ExerciseSessionRecord.EXERCISE_TYPE_SCUBA_DIVING -> R.string.scuba_diving
            ExerciseSessionRecord.EXERCISE_TYPE_SKATING -> R.string.skating
            ExerciseSessionRecord.EXERCISE_TYPE_SKIING -> R.string.skiing
            ExerciseSessionRecord.EXERCISE_TYPE_SNOWBOARDING -> R.string.snowboarding
            ExerciseSessionRecord.EXERCISE_TYPE_SNOWSHOEING -> R.string.snowshoeing
            ExerciseSessionRecord.EXERCISE_TYPE_SOCCER -> R.string.soccer
            ExerciseSessionRecord.EXERCISE_TYPE_SOFTBALL -> R.string.softball
            ExerciseSessionRecord.EXERCISE_TYPE_SQUASH -> R.string.squash
            ExerciseSessionRecord.EXERCISE_TYPE_STAIR_CLIMBING -> R.string.stair_climbing
            ExerciseSessionRecord.EXERCISE_TYPE_STAIR_CLIMBING_MACHINE -> R.string.stair_climbing_machine
            ExerciseSessionRecord.EXERCISE_TYPE_STRENGTH_TRAINING -> R.string.strength_training
            ExerciseSessionRecord.EXERCISE_TYPE_STRETCHING -> R.string.stretching
            ExerciseSessionRecord.EXERCISE_TYPE_SURFING -> R.string.surfing
            ExerciseSessionRecord.EXERCISE_TYPE_SWIMMING_OPEN_WATER -> R.string.swimming_open_water
            ExerciseSessionRecord.EXERCISE_TYPE_SWIMMING_POOL -> R.string.swimming_pool
            ExerciseSessionRecord.EXERCISE_TYPE_TABLE_TENNIS -> R.string.table_tennis
            ExerciseSessionRecord.EXERCISE_TYPE_TENNIS -> R.string.tennis
            ExerciseSessionRecord.EXERCISE_TYPE_VOLLEYBALL -> R.string.volleyball
            ExerciseSessionRecord.EXERCISE_TYPE_WALKING -> R.string.walking
            ExerciseSessionRecord.EXERCISE_TYPE_WATER_POLO -> R.string.water_polo
            ExerciseSessionRecord.EXERCISE_TYPE_WEIGHTLIFTING -> R.string.weightlifting
            ExerciseSessionRecord.EXERCISE_TYPE_WHEELCHAIR -> R.string.wheelchair
            ExerciseSessionRecord.EXERCISE_TYPE_YOGA -> R.string.yoga
            else -> R.string.unknown
        }
    )


@Preview(group = "Light Theme")
@Composable
fun ExerciseSessionRecordDisplayPreview()
{
    val exerciseSessionRecord = ExerciseSession.generateDummyData()[0]
    BienestarEmocionalTheme {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ExerciseSessionRecordDisplayPreviewDarkTheme()
{
    val exerciseSessionRecord = ExerciseSession.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun ExerciseSessionRecordDisplayLargeScreenPreview()
{
    val exerciseSessionRecord = ExerciseSession.generateDummyData()[0]
    BienestarEmocionalTheme {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ExerciseSessionRecordDisplayLargeScreenPreviewDarkTheme()
{
    val exerciseSessionRecord = ExerciseSession.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}