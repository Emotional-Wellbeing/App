package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodPressureRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodPressure
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [BloodPressureRecord]
 * @param windowSize: [WindowSize] to modify the component according to the screen
 */
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
        DrawPair(key = stringResource(R.string.body_position), value = decodeBodyPosition())
        DrawPair(key = stringResource(R.string.measurement_location), value = decodeMeasurementLocation())
        metadata.Display(windowSize)
    }
}

@Composable
fun BloodPressureRecord.decodeBodyPosition(): String =
    when(bodyPosition)
    {
        BloodPressureRecord.BODY_POSITION_UNKNOWN -> stringResource(R.string.unknown)
        BloodPressureRecord.BODY_POSITION_STANDING_UP -> stringResource(R.string.standing_up)
        BloodPressureRecord.BODY_POSITION_SITTING_DOWN -> stringResource(R.string.sitting_down)
        BloodPressureRecord.BODY_POSITION_LYING_DOWN -> stringResource(R.string.lying_down)
        BloodPressureRecord.BODY_POSITION_RECLINING -> stringResource(R.string.reclining)
        else -> stringResource(R.string.unknown)
    }

@Composable
fun BloodPressureRecord.decodeMeasurementLocation(): String =
    when(measurementLocation)
    {
        BloodPressureRecord.MEASUREMENT_LOCATION_UNKNOWN -> stringResource(R.string.unknown)
        BloodPressureRecord.MEASUREMENT_LOCATION_LEFT_WRIST ->
            stringResource(R.string.left_wrist)
        BloodPressureRecord.MEASUREMENT_LOCATION_RIGHT_WRIST ->
            stringResource(R.string.right_wrist)
        BloodPressureRecord.MEASUREMENT_LOCATION_LEFT_UPPER_ARM ->
            stringResource(R.string.left_upper_arm)
        BloodPressureRecord.MEASUREMENT_LOCATION_RIGHT_UPPER_ARM ->
            stringResource(R.string.right_upper_arm)
        else -> stringResource(R.string.unknown)
    }

@Preview(group = "Light Theme")
@Composable
fun BloodPressureRecordDisplayPreview()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodPressureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BloodPressureRecordDisplayPreviewDarkTheme()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodPressureRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Light Theme")
@Composable
fun BloodPressureRecordDisplayLargeScreenPreview()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodPressureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BloodPressureRecordDisplayLargeScreenPreviewDarkTheme()
{
    val bloodPressureRecord = BloodPressure.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodPressureRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}