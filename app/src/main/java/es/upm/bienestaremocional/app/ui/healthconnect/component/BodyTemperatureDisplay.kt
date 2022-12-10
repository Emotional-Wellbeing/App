package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation
import androidx.health.connect.client.records.BodyTemperatureRecord
import es.upm.bienestaremocional.R
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
    val unit = stringResource(id = R.string.celsius)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.temperature), value = "$temperatureFormatted $unit")
        decodeMeasurementLocation()?.let {
            DrawPair(key = stringResource(R.string.place), value = it)
        }
        metadata.Display(windowSize)
    }
}

@Composable
fun BodyTemperatureRecord.decodeMeasurementLocation(): String? =
    when(measurementLocation)
    {
        BodyTemperatureMeasurementLocation.ARMPIT -> stringResource(R.string.armpit)
        BodyTemperatureMeasurementLocation.FINGER -> stringResource(R.string.finger)
        BodyTemperatureMeasurementLocation.FOREHEAD -> stringResource(R.string.forehead)
        BodyTemperatureMeasurementLocation.MOUTH -> stringResource(R.string.mouth)
        BodyTemperatureMeasurementLocation.RECTUM -> stringResource(R.string.rectum)
        BodyTemperatureMeasurementLocation.TEMPORAL_ARTERY ->
            stringResource(R.string.temporal_artery)
        BodyTemperatureMeasurementLocation.TOE -> stringResource(R.string.toe)
        BodyTemperatureMeasurementLocation.EAR -> stringResource(R.string.ear)
        BodyTemperatureMeasurementLocation.WRIST -> stringResource(R.string.wrist)
        BodyTemperatureMeasurementLocation.VAGINA -> stringResource(R.string.vagina)
        else -> null
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