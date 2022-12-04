package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodGlucoseRecord
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
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = "Nivel: ", value = "$level mg/dL")
        specimenSource?.let {
            DrawPair(key = "Tipo de fluido utilizado: ", value = it)
        }
        mealType?.let {
            DrawPair(key = "Comida: ", value = it)
        }
        relationToMeal?.let {
            DrawPair(key = "Momento de la medición: ", value = it)
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
