package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.Vo2MaxRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.Vo2Max
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun Vo2MaxRecord.Display(windowSize: WindowSize)
{
    val vo2 = String.format("%.2f",vo2MillilitersPerMinuteKilogram)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = "Vo2 máx: ", value = "$vo2 ml/kg/min")
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun Vo2MaxRecordDisplayPreview()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme {
        vo2Max.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun Vo2MaxRecordDisplayPreviewDarkTheme()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        vo2Max.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun Vo2MaxRecordDisplayLargeScreenPreview()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme {
        vo2Max.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun Vo2MaxRecordDisplayLargeScreenPreviewDarkTheme()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        vo2Max.Display(windowSize = WindowSize.MEDIUM)
    }
}