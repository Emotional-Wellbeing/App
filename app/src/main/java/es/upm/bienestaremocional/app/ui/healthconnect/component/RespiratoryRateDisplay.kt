package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.RespiratoryRateRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.RespiratoryRate
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [RespiratoryRateRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun RespiratoryRateRecord.Display(widthSize: WindowWidthSizeClass)
{
    val unit = stringResource(id = R.string.bpm)

    val rateFormatted = String.format("%.2f",rate)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.frequency), value = "$rateFormatted $unit")
        metadata.Display(widthSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun RespiratoryRateRecordDisplayPreview()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        respiratoryRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun RespiratoryRateRecordDisplayPreviewDarkTheme()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        respiratoryRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun RespiratoryRateRecordDisplayLargeScreenPreview()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        respiratoryRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun RespiratoryRateRecordDisplayLargeScreenPreviewDarkTheme()
{
    val respiratoryRateRecord = RespiratoryRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        respiratoryRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}