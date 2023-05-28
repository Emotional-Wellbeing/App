package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.units.Mass
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.generateTime
import kotlin.random.Random

/**
 * Displays [WeightRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun WeightRecord.Display(widthSize: WindowWidthSizeClass)
{
    val weightFormatted = String.format("%.2f",weight.inKilograms)
    val unit = stringResource(id = R.string.kg)

    BasicCard {
        SeriesDateTimeHeading(
            time = time,
            zoneOffset = zoneOffset
        )

        DrawPair(key = stringResource(id = R.string.weight), value = "$weightFormatted $unit")

        metadata.Display(widthSize)
    }
}

private fun generateDummyData() : WeightRecord
{
    val time = generateTime()

    val weight = Mass.kilograms(Random.nextDouble(0.0,200.0))

    return WeightRecord(
        time = time.toInstant(),
        zoneOffset = time.offset,
        weight = weight
    )
}

@Preview(group = "Light Theme")
@Composable
fun WeightRecordDisplayPreview()
{
    val weightRecord = generateDummyData()
    BienestarEmocionalTheme {
        weightRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun WeightRecordDisplayPreviewDarkTheme()
{
    val weightRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        weightRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun WeightRecordDisplayLargeScreenPreview()
{
    val weightRecord = generateDummyData()
    BienestarEmocionalTheme {
        weightRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun WeightRecordDisplayLargeScreenPreviewDarkTheme()
{
    val weightRecord = generateDummyData()
    BienestarEmocionalTheme(darkTheme = true) {
        weightRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}