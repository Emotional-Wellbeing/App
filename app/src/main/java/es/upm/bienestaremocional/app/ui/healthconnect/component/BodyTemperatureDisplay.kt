package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_ARMPIT
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_EAR
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_FINGER
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_FOREHEAD
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_MOUTH
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_RECTUM
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_TEMPORAL_ARTERY
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_TOE
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_UNKNOWN
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_VAGINA
import androidx.health.connect.client.records.BodyTemperatureMeasurementLocation.MEASUREMENT_LOCATION_WRIST
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
        DrawPair(key = stringResource(R.string.place), value = decodeMeasurementLocation())
        metadata.Display(windowSize)
    }
}

@Composable
fun BodyTemperatureRecord.decodeMeasurementLocation(): String =
    when(measurementLocation)
    {
        MEASUREMENT_LOCATION_UNKNOWN -> stringResource(R.string.unknown)
        MEASUREMENT_LOCATION_ARMPIT -> stringResource(R.string.armpit)
        MEASUREMENT_LOCATION_FINGER -> stringResource(R.string.finger)
        MEASUREMENT_LOCATION_FOREHEAD -> stringResource(R.string.forehead)
        MEASUREMENT_LOCATION_MOUTH -> stringResource(R.string.mouth)
        MEASUREMENT_LOCATION_RECTUM -> stringResource(R.string.rectum)
        MEASUREMENT_LOCATION_TEMPORAL_ARTERY -> stringResource(R.string.temporal_artery)
        MEASUREMENT_LOCATION_TOE -> stringResource(R.string.toe)
        MEASUREMENT_LOCATION_EAR -> stringResource(R.string.ear)
        MEASUREMENT_LOCATION_WRIST -> stringResource(R.string.wrist)
        MEASUREMENT_LOCATION_VAGINA -> stringResource(R.string.vagina)
        else -> stringResource(R.string.unknown)
    }

@Preview(group = "Light Theme")
@Composable
fun BodyTemperatureRecordDisplayPreview()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme {
        bodyTemperatureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BodyTemperatureRecordDisplayPreviewDarkTheme()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bodyTemperatureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Light Theme")
@Composable
fun BodyTemperatureRecordDisplayLargeScreenPreview()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme {
        bodyTemperatureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BodyTemperatureRecordDisplayLargeScreenPreviewDarkTheme()
{
    val bodyTemperatureRecord = BodyTemperature.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bodyTemperatureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}