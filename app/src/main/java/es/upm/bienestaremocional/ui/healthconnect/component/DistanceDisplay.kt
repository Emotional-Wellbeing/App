package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.DistanceRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Displays [DistanceRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun DistanceRecord.Display(widthSize: WindowWidthSizeClass)
{
    val distanceFormatted  = String.format("%.2f",distance.inKilometers)
    val unit = stringResource(id = R.string.km)

    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        DrawPair(key = stringResource(id = R.string.distance), value = "$distanceFormatted $unit")

        metadata.Display(widthSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun DistanceRecordDisplayPreview()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun DistanceRecordDisplayPreviewDarkTheme()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun DistanceRecordDisplayLargeScreenPreview()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun DistanceRecordDisplayLargeScreenPreviewDarkTheme()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}