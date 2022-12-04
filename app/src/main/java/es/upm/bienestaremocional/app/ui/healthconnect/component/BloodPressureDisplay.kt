package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodPressureRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodPressure
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun BloodPressureRecord.Display(windowSize: WindowSize)
{
    val systolicFormatted  = String.format("%.2f",systolic.inMillimetersOfMercury)
    val diastolicFormatted = String.format("%.2f",diastolic.inMillimetersOfMercury)

    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = "Sistólica: ", value = "$systolicFormatted mmHg")
        DrawPair(key = "Diastólica: ", value = "$diastolicFormatted mmHg")
        bodyPosition?.let {
            DrawPair(key = "Posición corporal: ", value = it)
        }
        measurementLocation?.let {
            DrawPair(key = "Lugar de medición: ", value = it)
        }
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun BloodPressureRecordDisplayPreview()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodPressureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BloodPressureRecordDisplayPreviewDarkTheme()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodPressureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BloodPressureRecordDisplayLargeScreenPreview()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodPressureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun BloodPressureRecordDisplayLargeScreenPreviewDarkTheme()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodPressureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}