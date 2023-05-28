package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.health.connect.client.units.Length
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateInterval
import kotlin.random.Random

/**
 * Displays [ElevationGainedRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun ElevationGainedRecord.Display(widthSize: WindowWidthSizeClass)
{
    val elevationGainedFormatted  = String.format("%.2f",elevation.inMeters)
    val unit = stringResource(id = R.string.m)
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        DrawPair(key = stringResource(id = R.string.elevation_gained),
            value = "$elevationGainedFormatted $unit")

        metadata.Display(widthSize)
    }
}

private fun generateDummyData() : ElevationGainedRecord
{
    val (init, end) = generateInterval()

    val elevation = Length.meters(Random.nextDouble(0.0,2500.0))

    return ElevationGainedRecord(
        startTime = init.toInstant(),
        startZoneOffset = init.offset,
        endTime = end.toInstant(),
        endZoneOffset = end.offset,
        elevation = elevation
    )
}

@Preview(group = "Light Theme")
@Composable
fun ElevationGainedRecordDisplayPreview()
{
    val elevationGainedRecord = generateDummyData()
    BienestarEmocionalTheme {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ElevationGainedRecordDisplayPreviewDarkTheme()
{
    val elevationGainedRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun ElevationGainedRecordDisplayLargeScreenPreview()
{
    val elevationGainedRecord = generateDummyData()
    BienestarEmocionalTheme {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ElevationGainedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val elevationGainedRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}