package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BodyTemperatureRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.BodyTemperature
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun BodyTemperatureRecord.Display(windowSize: WindowSize)
{
    val temperatureFormatted = String.format("%.2f",temperature.inCelsius)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = "Temperatura: ", value = "$temperatureFormatted ºC")
        measurementLocation?.let { DrawPair(key = "Lugar: ", value = it) }
        metadata.Display(windowSize)
    }
}

@Preview
@Composable
fun BodyTemperatureRecordDisplayPreview()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme {
        bodyTemperatureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BodyTemperatureRecordDisplayPreviewDarkTheme()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bodyTemperatureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun BodyTemperatureRecordDisplayLargeScreenPreview()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme {
        bodyTemperatureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun BodyTemperatureRecordDisplayLargeScreenPreviewDarkTheme()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bodyTemperatureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}