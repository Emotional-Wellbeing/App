package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.FloorsClimbedRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Displays [FloorsClimbedRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun FloorsClimbedRecord.Display(widthSize: WindowWidthSizeClass)
{
    val floorsFormatted = String.format("%.2f",floors)

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

@Preview(group = "Light Theme")
@Composable
fun FloorsClimbedRecordDisplayPreview()
{
    val floorsClimbedRecord = FloorsClimbed.generateDummyData()[0]
    BienestarEmocionalTheme {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun FloorsClimbedRecordDisplayPreviewDarkTheme()
{
    val floorsClimbedRecord = FloorsClimbed.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun FloorsClimbedRecordDisplayLargeScreenPreview()
{
    val floorsClimbedRecord = FloorsClimbed.generateDummyData()[0]
    BienestarEmocionalTheme {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun FloorsClimbedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val floorsClimbedRecord = FloorsClimbed.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        floorsClimbedRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}