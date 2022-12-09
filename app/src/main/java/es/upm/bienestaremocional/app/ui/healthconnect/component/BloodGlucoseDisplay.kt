package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodGlucoseRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodGlucose
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun BloodGlucoseRecord.Display(windowSize: WindowSize)
{
    val level = String.format("%.2f",level.inMilligramsPerDeciliter)
    val unit = stringResource(id = R.string.mg_dL)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.level), value = "$level $unit")
        specimenSource?.let {
            DrawPair(key = stringResource(R.string.fluid_type), value = it)
        }
        mealType?.let {
            DrawPair(key = stringResource(R.string.meal), value = it)
        }
        relationToMeal?.let {
            DrawPair(key = stringResource(R.string.time_measurement), value = it)
        }
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun BloodGlucoseRecordDisplayPreview()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodGlucoseRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BloodGlucoseRecordDisplayPreviewDarkTheme()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodGlucoseRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BloodGlucoseRecordDisplayLargeScreenPreview()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodGlucoseRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun BloodGlucoseRecordDisplayLargeScreenPreviewDarkTheme()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodGlucoseRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
