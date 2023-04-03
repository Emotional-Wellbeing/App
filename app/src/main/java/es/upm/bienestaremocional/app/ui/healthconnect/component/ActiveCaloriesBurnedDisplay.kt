package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.ActiveCaloriesBurned
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [ActiveCaloriesBurnedRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun ActiveCaloriesBurnedRecord.Display(widthSize: WindowWidthSizeClass)
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
        DrawPair(key = stringResource(R.string.calories_burnt), value = "$kcal $unit")
        metadata.Display(widthSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun ActiveCaloriesBurnedRecordDisplayPreview()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ActiveCaloriesBurnedRecordDisplayPreviewDarkTheme()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun ActiveCaloriesBurnedRecordDisplayLargeScreenPreview()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun ActiveCaloriesBurnedRecordDisplayLargeScreenPreviewDarkTheme()
{
    val basalMetabolicRateRecord = ActiveCaloriesBurned.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}