package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.StepsRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Displays [StepsRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun StepsRecord.Display(widthSize: WindowWidthSizeClass) {
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

@Preview(group = "Light Theme")
@Composable
fun StepsRecordDisplayPreview() {
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun StepsRecordDisplayPreviewDarkTheme() {
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Light Theme")
@Composable
fun StepsRecordDisplayLargeScreenPreview() {
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun StepsRecordDisplayLargeScreenPreviewDarkTheme() {
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        stepsRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
