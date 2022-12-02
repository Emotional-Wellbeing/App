package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.DistanceRecord
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
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        DrawPair(key = "Distancia: ", value = "$distanceFormatted km")

        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun DistanceRecordDisplayPreview()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme {
        distanceRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun DistanceRecordDisplayPreviewDarkTheme()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun DistanceRecordDisplayLargeScreenPreview()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme {
        distanceRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun DistanceRecordDisplayLargeScreenPreviewDarkTheme()
{
    val distanceRecord = Distance.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}