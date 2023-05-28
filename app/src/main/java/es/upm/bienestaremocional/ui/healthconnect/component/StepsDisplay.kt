package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.StepsRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateInterval
import kotlin.random.Random

/**
 * Displays [StepsRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun StepsRecord.Display(widthSize: WindowWidthSizeClass)
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        DrawPair(key = stringResource(id = R.string.steps), value = count.toString())

        metadata.Display(widthSize)
    }
}

private fun generateDummyData() : StepsRecord
{
    val (init, end) = generateInterval()
    val count = Random.nextLong(0,20000)
    return StepsRecord(
        startTime = init.toInstant(),
        startZoneOffset = init.offset,
        endTime = end.toInstant(),
        endZoneOffset = end.offset,
        count = count
    )
}

@Preview(group = "Light Theme")
@Composable
fun StepsRecordDisplayPreview()
{
    val stepsRecord = generateDummyData()
    BienestarEmocionalTheme {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun StepsRecordDisplayPreviewDarkTheme()
{
    val stepsRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun StepsRecordDisplayLargeScreenPreview()
{
    val stepsRecord = generateDummyData()
    BienestarEmocionalTheme {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun StepsRecordDisplayLargeScreenPreviewDarkTheme()
{
    val stepsRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
