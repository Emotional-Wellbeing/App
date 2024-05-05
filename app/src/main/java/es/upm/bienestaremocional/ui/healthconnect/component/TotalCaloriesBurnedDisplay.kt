package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.units.Energy
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateInterval
import kotlin.random.Random

/**
 * Displays [TotalCaloriesBurnedRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun TotalCaloriesBurnedRecord.Display(widthSize: WindowWidthSizeClass) {
    val kcal = String.format("%.2f", energy.inKilocalories)
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

private fun generateDummyData(): TotalCaloriesBurnedRecord {
    val (init, end) = generateInterval()
    val energy = Energy.kilocalories(Random.nextDouble(1000.0, 5000.0))
    return TotalCaloriesBurnedRecord(
        startTime = init.toInstant(),
        startZoneOffset = init.offset,
        endTime = end.toInstant(),
        endZoneOffset = end.offset,
        energy = energy
    )
}

@Preview(group = "Light Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayPreview() {
    val basalMetabolicRateRecord = generateDummyData()
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayPreviewDarkTheme() {
    val basalMetabolicRateRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(group = "Light Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayLargeScreenPreview() {
    val basalMetabolicRateRecord = generateDummyData()
    BienestarEmocionalTheme {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(group = "Dark Theme")
@Composable
fun TotalCaloriesBurnedRecordDisplayLargeScreenPreviewDarkTheme() {
    val basalMetabolicRateRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        basalMetabolicRateRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}