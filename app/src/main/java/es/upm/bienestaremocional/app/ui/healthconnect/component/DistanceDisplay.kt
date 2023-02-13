package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.DistanceRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun DistanceRecord.Display(windowSize: WindowSize)
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

        metadata.Display(windowSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun DistanceRecordDisplayPreview()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme {
        distanceRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun DistanceRecordDisplayPreviewDarkTheme()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Light Theme")
@Composable
fun DistanceRecordDisplayLargeScreenPreview()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme {
        distanceRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun DistanceRecordDisplayLargeScreenPreviewDarkTheme()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}