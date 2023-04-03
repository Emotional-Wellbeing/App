package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.OxygenSaturationRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.OxygenSaturation
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Displays [OxygenSaturationRecord]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun OxygenSaturationRecord.Display(widthSize: WindowWidthSizeClass)
{
    val unit = stringResource(id = R.string.percentage)

    val percentageFormatted = String.format("%.2f",percentage.value)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        DrawPair(key = stringResource(R.string.saturation), value = "$percentageFormatted $unit")
        metadata.Display(widthSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun OxygenSaturationRecordDisplayPreview()
{
    val oxygenSaturationRecord = OxygenSaturation.generateDummyData()[0]
    BienestarEmocionalTheme {
        oxygenSaturationRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun OxygenSaturationRecordDisplayPreviewDarkTheme()
{
    val oxygenSaturationRecord = OxygenSaturation.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        oxygenSaturationRecord.Display(widthSize = WindowWidthSizeClass.Compact)
    }
}
@Preview(group = "Light Theme")
@Composable
fun OxygenSaturationRecordDisplayLargeScreenPreview()
{
    val oxygenSaturationRecord = OxygenSaturation.generateDummyData()[0]
    BienestarEmocionalTheme {
        oxygenSaturationRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun OxygenSaturationRecordDisplayLargeScreenPreviewDarkTheme()
{
    val oxygenSaturationRecord = OxygenSaturation.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        oxygenSaturationRecord.Display(widthSize = WindowWidthSizeClass.Medium)
    }
}