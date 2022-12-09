package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.RestingHeartRateRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.RestingHeartRate
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun RestingHeartRateRecord.Display(windowSize: WindowSize)
{
    val unit = stringResource(id = R.string.bpm)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(id = R.string.frequency), value = "$beatsPerMinute $unit")
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun RestingHeartRateRecordDisplayPreview()
{
    val restingHeartRateRecord = RestingHeartRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        restingHeartRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun RestingHeartRateRecordDisplayPreviewDarkTheme()
{
    val restingHeartRateRecord = RestingHeartRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        restingHeartRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun RestingHeartRateRecordDisplayLargeScreenPreview()
{
    val restingHeartRateRecord = RestingHeartRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        restingHeartRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun RestingHeartRateRecordDisplayLargeScreenPreviewDarkTheme()
{
    val restingHeartRateRecord = RestingHeartRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        restingHeartRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}