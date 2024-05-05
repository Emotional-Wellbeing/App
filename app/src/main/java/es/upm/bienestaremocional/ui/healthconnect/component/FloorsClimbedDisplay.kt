package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.FloorsClimbedRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateInterval
import kotlin.random.Random

/**
 * Displays [FloorsClimbedRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun FloorsClimbedRecord.Display(widthSize: WindowWidthSizeClass) {
    val floorsFormatted = String.format("%.2f", floors)

    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        DrawPair(key = stringResource(id = R.string.floors), value = floorsFormatted)

        metadata.Display(widthSize)
    }
}

private fun generateDummyData(): FloorsClimbedRecord {
    val (init, end) = generateInterval()

    val floors = Random.nextDouble(0.0, 1000000.0)

    return FloorsClimbedRecord(
        startTime = init.toInstant(),
        startZoneOffset = init.offset,
        endTime = end.toInstant(),
        endZoneOffset = end.offset,
        floors = floors
    )
}

@Preview(group = "Light Theme")
@Composable
fun FloorsClimbedRecordDisplayPreview() {
    val floorsClimbedRecord = generateDummyData()
    BienestarEmocionalTheme {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun FloorsClimbedRecordDisplayPreviewDarkTheme() {
    val floorsClimbedRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Light Theme")
@Composable
fun FloorsClimbedRecordDisplayLargeScreenPreview() {
    val floorsClimbedRecord = generateDummyData()
    BienestarEmocionalTheme {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun FloorsClimbedRecordDisplayLargeScreenPreviewDarkTheme() {
    val floorsClimbedRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}