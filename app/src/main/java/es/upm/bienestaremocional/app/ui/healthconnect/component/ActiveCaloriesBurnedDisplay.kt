package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.ActiveCaloriesBurned
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun ActiveCaloriesBurnedRecord.Display(windowSize: WindowSize)
{
    val kcal = String.format("%.2f",energy.inKilocalories)
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        DrawPair(key = "Calorias quemadas: ", value = "$kcal kcal")
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun ActiveCaloriesBurnedRecordDisplayPreview()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun ActiveCaloriesBurnedRecordDisplayPreviewDarkTheme()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun ActiveCaloriesBurnedRecordDisplayLargeScreenPreview()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun ActiveCaloriesBurnedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}