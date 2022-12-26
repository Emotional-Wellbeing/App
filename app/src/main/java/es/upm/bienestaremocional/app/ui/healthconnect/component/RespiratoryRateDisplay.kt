package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.RespiratoryRateRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.RespiratoryRate
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun RespiratoryRateRecord.Display(windowSize: WindowSize)
{
    val unit = stringResource(id = R.string.bpm)

    val rateFormatted = String.format("%.2f",rate)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.frequency), value = "$rateFormatted $unit")
        metadata.Display(windowSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun RespiratoryRateRecordDisplayPreview()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        respiratoryRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun RespiratoryRateRecordDisplayPreviewDarkTheme()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        respiratoryRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Light Theme")
@Composable
fun RespiratoryRateRecordDisplayLargeScreenPreview()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        respiratoryRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun RespiratoryRateRecordDisplayLargeScreenPreviewDarkTheme()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        respiratoryRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}