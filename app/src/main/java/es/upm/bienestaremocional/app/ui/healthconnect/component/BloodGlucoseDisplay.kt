package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.RELATION_TO_MEAL_AFTER_MEAL
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.RELATION_TO_MEAL_BEFORE_MEAL
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.RELATION_TO_MEAL_FASTING
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.RELATION_TO_MEAL_GENERAL
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.RELATION_TO_MEAL_UNKNOWN
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_CAPILLARY_BLOOD
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_INTERSTITIAL_FLUID
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_PLASMA
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_SERUM
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_TEARS
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_UNKNOWN
import androidx.health.connect.client.records.BloodGlucoseRecord.Companion.SPECIMEN_SOURCE_WHOLE_BLOOD
import androidx.health.connect.client.records.MealType.MEAL_TYPE_BREAKFAST
import androidx.health.connect.client.records.MealType.MEAL_TYPE_DINNER
import androidx.health.connect.client.records.MealType.MEAL_TYPE_LUNCH
import androidx.health.connect.client.records.MealType.MEAL_TYPE_SNACK
import androidx.health.connect.client.records.MealType.MEAL_TYPE_UNKNOWN
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodGlucose
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [BloodGlucoseRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun BloodGlucoseRecord.Display(widthSize: WindowWidthSizeClass)
{
    val level = String.format("%.2f",level.inMilligramsPerDeciliter)
    val unit = stringResource(id = R.string.mg_dL)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.level), value = "$level $unit")
        DrawPair(key = stringResource(R.string.fluid_type), value = decodeSpecimenSource())
        DrawPair(key = stringResource(R.string.meal), value = decodeMealType())
        DrawPair(key = stringResource(R.string.time_measurement), value = decodeRelationToMeal())
        metadata.Display(widthSize)
    }
}

@Composable
fun BloodGlucoseRecord.decodeSpecimenSource(): String =
    when(specimenSource)
    {
        SPECIMEN_SOURCE_UNKNOWN -> stringResource(R.string.unknown)
        SPECIMEN_SOURCE_INTERSTITIAL_FLUID -> stringResource(R.string.interstitial_fluid)
        SPECIMEN_SOURCE_CAPILLARY_BLOOD -> stringResource(R.string.capillary_blood)
        SPECIMEN_SOURCE_PLASMA -> stringResource(R.string.plasma)
        SPECIMEN_SOURCE_SERUM -> stringResource(R.string.serum)
        SPECIMEN_SOURCE_TEARS -> stringResource(R.string.tears)
        SPECIMEN_SOURCE_WHOLE_BLOOD -> stringResource(R.string.whole_blood)
        else -> stringResource(R.string.unknown)
    }

@Composable
fun BloodGlucoseRecord.decodeMealType(): String =
    when(mealType)
    {
        MEAL_TYPE_UNKNOWN -> stringResource(R.string.unknown)
        MEAL_TYPE_BREAKFAST -> stringResource(R.string.breakfast)
        MEAL_TYPE_LUNCH -> stringResource(R.string.lunch)
        MEAL_TYPE_DINNER -> stringResource(R.string.dinner)
        MEAL_TYPE_SNACK -> stringResource(R.string.snack)
        else -> stringResource(R.string.unknown)
    }

@Composable
fun BloodGlucoseRecord.decodeRelationToMeal(): String =
    when(relationToMeal)
    {
        RELATION_TO_MEAL_UNKNOWN -> stringResource(R.string.unknown)
        RELATION_TO_MEAL_GENERAL -> stringResource(R.string.general)
        RELATION_TO_MEAL_FASTING -> stringResource(R.string.fasting)
        RELATION_TO_MEAL_BEFORE_MEAL -> stringResource(R.string.before_meal)
        RELATION_TO_MEAL_AFTER_MEAL -> stringResource(R.string.after_meal)
        else -> stringResource(R.string.unknown)
    }
@Preview(group = "Light Theme")
@Composable
fun BloodGlucoseRecordDisplayPreview()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodGlucoseRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BloodGlucoseRecordDisplayPreviewDarkTheme()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodGlucoseRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun BloodGlucoseRecordDisplayLargeScreenPreview()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme {
        bloodGlucoseRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun BloodGlucoseRecordDisplayLargeScreenPreviewDarkTheme()
{
    val bloodGlucoseRecord = BloodGlucose.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        bloodGlucoseRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
