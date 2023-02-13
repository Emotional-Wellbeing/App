package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.Vo2MaxRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.Vo2Max
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun Vo2MaxRecord.Display(windowSize: WindowSize)
{
    val vo2 = String.format("%.2f",vo2MillilitersPerMinuteKilogram)
    val unit = stringResource(id = R.string.ml_kg_min)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(id = R.string.vo2_max), value = "$vo2 $unit")
        DrawPair(key = stringResource(R.string.measurement_method), value = decodeMeasurementMethod())
        metadata.Display(windowSize)
    }
}

@Composable
fun Vo2MaxRecord.decodeMeasurementMethod(): String =
    when(measurementMethod)
    {
        Vo2MaxRecord.MEASUREMENT_METHOD_OTHER -> stringResource(R.string.other)
        Vo2MaxRecord.MEASUREMENT_METHOD_METABOLIC_CART -> stringResource(R.string.metabolic_cart)
        Vo2MaxRecord.MEASUREMENT_METHOD_HEART_RATE_RATIO ->
            stringResource(R.string.heart_rate_ratio)
        Vo2MaxRecord.MEASUREMENT_METHOD_COOPER_TEST -> stringResource(R.string.cooper_test)
        Vo2MaxRecord.MEASUREMENT_METHOD_MULTISTAGE_FITNESS_TEST ->
            stringResource(R.string.multistage_fitness_test)
        Vo2MaxRecord.MEASUREMENT_METHOD_ROCKPORT_FITNESS_TEST ->
            stringResource(R.string.rockport_fitness_test)
        else -> stringResource(R.string.unknown)
    }

@Preview
@Composable
fun Vo2MaxRecordDisplayPreview()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme {
        vo2Max.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun Vo2MaxRecordDisplayPreviewDarkTheme()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        vo2Max.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview
@Composable
fun Vo2MaxRecordDisplayLargeScreenPreview()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme {
        vo2Max.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview
@Composable
fun Vo2MaxRecordDisplayLargeScreenPreviewDarkTheme()
{
    val vo2Max = Vo2Max.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        vo2Max.Display(windowSize = WindowSize.MEDIUM)
    }
}