package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.units.Length
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateInterval
import kotlin.random.Random

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

private fun generateDummyData(): DistanceRecord
{
    val (init, end) = generateInterval()

    val distance = Length.kilometers(Random.nextDouble(0.0,10.0))

    return DistanceRecord(
        startTime = init.toInstant(),
        startZoneOffset = init.offset,
        endTime = end.toInstant(),
        endZoneOffset = end.offset,
        distance = distance
    )
}

@Preview(group = "Light Theme")
@Composable
fun DistanceRecordDisplayPreview()
{
    val distanceRecord = generateDummyData()
    BienestarEmocionalTheme {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun DistanceRecordDisplayPreviewDarkTheme()
{
    val distanceRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun DistanceRecordDisplayLargeScreenPreview()
{
    val distanceRecord = generateDummyData()
    BienestarEmocionalTheme {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun DistanceRecordDisplayLargeScreenPreviewDarkTheme()
{
    val distanceRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        distanceRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}