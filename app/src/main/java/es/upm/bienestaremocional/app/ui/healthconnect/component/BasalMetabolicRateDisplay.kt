package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.BasalMetabolicRate
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun BasalMetabolicRateRecord.Display(windowSize: WindowSize)
{
    val imb = String.format("%.2f",basalMetabolicRate.inKilocaloriesPerDay)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = "IMB: ", value = "$imb kcal/día")
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun BasalMetabolicRateRecordDisplayPreview()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BasalMetabolicRateRecordDisplayPreviewDarkTheme()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BasalMetabolicRateRecordDisplayLargeScreenPreview()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun BasalMetabolicRateRecordDisplayLargeScreenPreviewDarkTheme()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}