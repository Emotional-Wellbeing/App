package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_AWAKE
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_DEEP
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_LIGHT
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_OUT_OF_BED
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_REM
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_SLEEPING
import androidx.health.connect.client.records.SleepSessionRecord.Companion.STAGE_TYPE_UNKNOWN
import androidx.health.connect.client.records.SleepSessionRecord.Stage
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.formatDisplayTimeStartEnd
import es.upm.bienestaremocional.utils.formatHoursMinutes
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Duration
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

private const val SLEEP_STAGE_TYPES = 8

/**
 * Displays [SleepSessionData]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun SleepSessionData.Display(widthSize: WindowWidthSizeClass) {
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        title?.let {
            DrawPair(key = stringResource(R.string.title), value = it)
        }
        notes?.let {
            DrawPair(key = stringResource(R.string.notes), value = it)
        }

        duration?.let {
            val formattedDuration = duration.formatHoursMinutes()
            DrawPair(key = stringResource(R.string.duration), value = formattedDuration)
        }

        Text(
            text = stringResource(R.string.sleep_stages),
            color = MaterialTheme.colorScheme.onSurface
        )
        stages.forEach { it.Display() }

        metadata.Display(widthSize)
    }
}

@Composable
fun Stage.Display() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    )
    {
        val intervalLabel = formatDisplayTimeStartEnd(
            startTime, null, endTime, null
        )

        Text(
            modifier = Modifier.weight(1f),
            text = intervalLabel,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.weight(1f),
            text = decode(),
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
fun Stage.decode(): String =
    when (stage) {
        STAGE_TYPE_UNKNOWN -> stringResource(R.string.unknown)
        STAGE_TYPE_AWAKE -> stringResource(R.string.awake)
        STAGE_TYPE_SLEEPING -> stringResource(R.string.sleeping)
        STAGE_TYPE_OUT_OF_BED -> stringResource(R.string.out_of_bed)
        STAGE_TYPE_LIGHT -> stringResource(R.string.light)
        STAGE_TYPE_DEEP -> stringResource(R.string.deep)
        STAGE_TYPE_REM -> stringResource(R.string.rem)
        else -> stringResource(R.string.unknown)
    }


/**
 * Generates a random sleep stage for the purpose of populating data.
 */
private fun randomSleepStage() = Random.nextInt(SLEEP_STAGE_TYPES)

/**
 * Creates a random list of sleep stages that spans the specified [start] to [end] time.
 */
private fun generateSleepStages(start: ZonedDateTime, end: ZonedDateTime):
        List<Stage> {
    val sleepStages = mutableListOf<Stage>()
    var stageStart = start
    while (stageStart < end) {
        val stageEnd = stageStart.plusMinutes(Random.nextLong(30, 120))
        val checkedEnd = if (stageEnd > end) end else stageEnd
        sleepStages.add(
            Stage(
                stage = randomSleepStage(),
                startTime = stageStart.toInstant(),
                endTime = checkedEnd.toInstant(),
            )
        )
        stageStart = checkedEnd
    }
    return sleepStages
}

private fun generateDummyData(): SleepSessionData {
    val notes = listOf(
        "I slept great!",
        "I got woken up",
        "Struggled to sleep",
        "Much needed sleep",
        "Restful sleep"
    )

    val (bedtime, wakeUp) = generateInterval(offsetDays = 1)
    val sleepStages = generateSleepStages(bedtime, wakeUp)
    return SleepSessionData(
        uid = "",
        title = "Zzz",
        duration = Duration.of(
            sleepStages.sumOf { it.endTime.epochSecond - it.startTime.epochSecond },
            ChronoUnit.SECONDS
        ),
        notes = notes[Random.nextInt(0, notes.size)],
        startTime = bedtime.toInstant(),
        startZoneOffset = bedtime.offset,
        endTime = wakeUp.toInstant(),
        endZoneOffset = wakeUp.offset,
        stages = sleepStages
    )
}

@Preview(group = "Light Theme")
@Composable
fun SleepSessionDataDisplayPreview() {
    val sleepSessionData = generateDummyData()
    BienestarEmocionalTheme {
        sleepSessionData.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun SleepSessionDataDisplayPreviewDarkTheme() {
    val sleepSessionData = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        sleepSessionData.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Light Theme")
@Composable
fun SleepSessionDataDisplayLargeScreenPreview() {
    val sleepSessionData = generateDummyData()
    BienestarEmocionalTheme {
        sleepSessionData.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun SleepSessionDataDisplayLargeScreenPreviewDarkTheme() {
    val sleepSessionData = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        sleepSessionData.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(group = "Light Theme")
@Composable
fun SleepStageDisplayPreview() {
    val stage = generateDummyData().stages[0]
    BienestarEmocionalTheme {
        stage.Display()
    }
}

@Preview(group = "Dark Theme")
@Composable
fun SleepStageDisplayPreviewDarkTheme() {
    val stage = generateDummyData().stages[0]
    BienestarEmocionalTheme(darkTheme = true) {
        stage.Display()
    }
}