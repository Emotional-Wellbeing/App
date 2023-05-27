package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Displays [TotalCaloriesBurnedRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun TotalCaloriesBurnedRecord.Display(widthSize: WindowWidthSizeClass)
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
        metadata.Display(widthSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayPreview()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayPreviewDarkTheme()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayLargeScreenPreview()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val basalMetabolicRateRecord = TotalCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}