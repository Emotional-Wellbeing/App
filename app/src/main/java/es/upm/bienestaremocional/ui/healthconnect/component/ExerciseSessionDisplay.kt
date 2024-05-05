package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.ExerciseSessionRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateInterval
import kotlin.random.Random

/**
 * Displays [ExerciseSessionRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun ExerciseSessionRecord.Display(widthSize: WindowWidthSizeClass) {
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
    stringResource(
        when (exerciseType) {
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

private fun generateDummyData(): ExerciseSessionRecord {
    val (init, end) = generateInterval()

    val exerciseType = when (Random.nextInt(0, 61)) {
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

    return ExerciseSessionRecord(
        startTime = init.toInstant(),
        startZoneOffset = init.offset,
        endTime = end.toInstant(),
        endZoneOffset = end.offset,
        exerciseType = exerciseType,
        title = "Exercise",
        notes = "Notes"
    )
}

@Preview(group = "Light Theme")
@Composable
fun ExerciseSessionRecordDisplayPreview() {
    val exerciseSessionRecord = generateDummyData()
    BienestarEmocionalTheme {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun ExerciseSessionRecordDisplayPreviewDarkTheme() {
    val exerciseSessionRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Light Theme")
@Composable
fun ExerciseSessionRecordDisplayLargeScreenPreview() {
    val exerciseSessionRecord = generateDummyData()
    BienestarEmocionalTheme {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun ExerciseSessionRecordDisplayLargeScreenPreviewDarkTheme() {
    val exerciseSessionRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        exerciseSessionRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}