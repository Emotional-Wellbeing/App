package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.ElevationGainedRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

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

@Preview(group = "Light Theme")
@Composable
fun ElevationGainedRecordDisplayPreview()
{
    val elevationGainedRecord = ElevationGained.generateDummyData()[0]
    BienestarEmocionalTheme {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ElevationGainedRecordDisplayPreviewDarkTheme()
{
    val elevationGainedRecord = ElevationGained.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun ElevationGainedRecordDisplayLargeScreenPreview()
{
    val elevationGainedRecord = ElevationGained.generateDummyData()[0]
    BienestarEmocionalTheme {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ElevationGainedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val elevationGainedRecord = ElevationGained.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        elevationGainedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}