package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.MealType
import androidx.health.connect.client.records.RelationToMeal
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
        decodeSpecimenSource()?.let {
            DrawPair(key = stringResource(R.string.fluid_type), value = it)
        }
        decodeMealType()?.let {
            DrawPair(key = stringResource(R.string.meal), value = it)
        }
        decodeRelationToMeal()?.let {
            DrawPair(key = stringResource(R.string.time_measurement), value = it)
        }
        metadata.Display(windowSize)
    }
}

@Composable
fun BloodGlucoseRecord.decodeSpecimenSource(): String? =
    when(specimenSource)
    {
        BloodGlucoseRecord.SpecimenSource.INTERSTITIAL_FLUID -> 
            stringResource(R.string.interstitial_fluid)
        BloodGlucoseRecord.SpecimenSource.CAPILLARY_BLOOD ->
            stringResource(R.string.capillary_blood)
        BloodGlucoseRecord.SpecimenSource.PLASMA ->
            stringResource(R.string.plasma)
        BloodGlucoseRecord.SpecimenSource.SERUM ->
            stringResource(R.string.serum)
        BloodGlucoseRecord.SpecimenSource.TEARS ->
            stringResource(R.string.tears)
        BloodGlucoseRecord.SpecimenSource.WHOLE_BLOOD ->
            stringResource(R.string.whole_blood)
        else -> null
    }

@Composable
fun BloodGlucoseRecord.decodeMealType(): String? =
    when(mealType)
    {
        MealType.UNKNOWN -> stringResource(R.string.unknown)
        MealType.BREAKFAST -> stringResource(R.string.breakfast)
        MealType.LUNCH -> stringResource(R.string.lunch)
        MealType.DINNER -> stringResource(R.string.dinner)
        MealType.SNACK -> stringResource(R.string.snack)
        else -> null
    }

@Composable
fun BloodGlucoseRecord.decodeRelationToMeal(): String? =
    when(mealType)
    {
        RelationToMeal.GENERAL -> stringResource(R.string.general)
        RelationToMeal.FASTING -> stringResource(R.string.fasting)
        RelationToMeal.BEFORE_MEAL -> stringResource(R.string.before_meal)
        RelationToMeal.AFTER_MEAL -> stringResource(R.string.after_meal)
        else -> null
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
