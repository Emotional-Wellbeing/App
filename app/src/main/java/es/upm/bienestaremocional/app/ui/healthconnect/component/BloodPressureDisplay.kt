package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.BodyPosition
import es.upm.bienestaremocional.R
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
    val unit = stringResource(id = R.string.mmHg)

    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.systolic), value = "$systolicFormatted $unit")
        DrawPair(key = stringResource(R.string.diastolic), value = "$diastolicFormatted $unit")
        decodeBodyPosition()?.let {
            DrawPair(key = stringResource(R.string.body_position), value = it)
        }
        decodeMeasurementLocation()?.let {
            DrawPair(key = stringResource(R.string.measurement_location), value = it)
        }
        metadata.Display(windowSize)
    }
}

@Composable
fun BloodPressureRecord.decodeBodyPosition(): String? =
    when(bodyPosition)
    {
        BodyPosition.STANDING_UP -> stringResource(R.string.standing_up)
        BodyPosition.SITTING_DOWN -> stringResource(R.string.sitting_down)
        BodyPosition.LYING_DOWN -> stringResource(R.string.lying_down)
        BodyPosition.RECLINING -> stringResource(R.string.reclining)
        else -> null
    }

@Composable
fun BloodPressureRecord.decodeMeasurementLocation(): String? =
    when(measurementLocation)
    {
        BloodPressureRecord.MeasurementLocation.LEFT_WRIST ->
            stringResource(R.string.left_wrist)
        BloodPressureRecord.MeasurementLocation.RIGHT_WRIST ->
            stringResource(R.string.right_wrist)
        BloodPressureRecord.MeasurementLocation.LEFT_UPPER_ARM ->
            stringResource(R.string.left_upper_arm)
        BloodPressureRecord.MeasurementLocation.RIGHT_UPPER_ARM ->
            stringResource(R.string.right_upper_arm)
        else -> null
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