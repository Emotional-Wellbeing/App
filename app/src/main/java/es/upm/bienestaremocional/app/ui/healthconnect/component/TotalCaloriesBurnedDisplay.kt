package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun TotalCaloriesBurnedRecord.Display(windowSize: WindowSize)
{
    val kcal = String.format("%.2f",energy.inKilocalories)
    val unit = stringResource(id = R.string.kcal)
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        DrawPair(key = stringResource(id = R.string.calories_burnt), value = "$kcal $unit")
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun TotalCaloriesBurnedRecordDisplayPreview()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun TotalCaloriesBurnedRecordDisplayPreviewDarkTheme()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun TotalCaloriesBurnedRecordDisplayLargeScreenPreview()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun TotalCaloriesBurnedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}