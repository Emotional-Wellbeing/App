package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.StepsRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun StepsRecord.Display(windowSize: WindowSize)
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        DrawPair(key = stringResource(id = R.string.steps), value = count.toString())

        metadata.Display(windowSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun StepsRecordDisplayPreview()
{
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme {
        stepsRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun StepsRecordDisplayPreviewDarkTheme()
{
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        stepsRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Light Theme")
@Composable
fun StepsRecordDisplayLargeScreenPreview()
{
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme {
        stepsRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun StepsRecordDisplayLargeScreenPreviewDarkTheme()
{
    val stepsRecord = Steps.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        stepsRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
