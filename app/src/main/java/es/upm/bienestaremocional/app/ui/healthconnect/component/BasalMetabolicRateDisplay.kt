package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.BasalMetabolicRate
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [BasalMetabolicRateRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun BasalMetabolicRateRecord.Display(widthSize: WindowWidthSizeClass)
{
    val imb = String.format("%.2f",basalMetabolicRate.inKilocaloriesPerDay)
    val unit = stringResource(id = R.string.kcal_day)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.bmr_literal), value = "$imb $unit")
        metadata.Display(widthSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun BasalMetabolicRateRecordDisplayPreview()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BasalMetabolicRateRecordDisplayPreviewDarkTheme()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun BasalMetabolicRateRecordDisplayLargeScreenPreview()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BasalMetabolicRateRecordDisplayLargeScreenPreviewDarkTheme()
{
    val basalMetabolicRateRecord = BasalMetabolicRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}