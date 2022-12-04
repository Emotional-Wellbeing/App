package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.SleepStageRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatDisplayTimeStartEnd
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatHoursMinutes
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun SleepSessionData.Display(windowSize: WindowSize)
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        title?.let {
            DrawPair(key = "Titulo: ", value = it)
        }
        notes?.let {
            DrawPair(key = "Notas: ", value = it)
        }

        duration?.let {
            val formattedDuration = duration.formatHoursMinutes()
            DrawPair(key = "Duración: ", value = formattedDuration)
        }

        Text(text = "Etapas del sueño:",color = MaterialTheme.colorScheme.onSurface)
        stages.forEach{it.Display()}

        metadata.Display(windowSize)
    }
}

@Composable
fun SleepStageRecord.Display()
{
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp))
    {
        val intervalLabel = formatDisplayTimeStartEnd(
            startTime, startZoneOffset, endTime, endZoneOffset)

        Text(modifier = Modifier.weight(1f),
            text = intervalLabel,
            color = MaterialTheme.colorScheme.primary)
        Text(modifier = Modifier.weight(1f),
            text = stage,
            color = MaterialTheme.colorScheme.tertiary)
    }
}

@Preview
@Composable
fun SleepSessionDataDisplayPreview()
{
    val sleepSessionData = Sleep.generateDummyData()[0]
    BienestarEmocionalTheme {
        sleepSessionData.Display(windowSize = WindowSize.COMPACT)
    }
}

@Preview
@Composable
fun SleepSessionDataDisplayPreviewDarkTheme()
{
    val sleepSessionData = Sleep.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        sleepSessionData.Display(windowSize = WindowSize.COMPACT)
    }
}

@Preview
@Composable
fun SleepSessionDataDisplayLargeScreenPreview()
{
    val sleepSessionData = Sleep.generateDummyData()[0]
    BienestarEmocionalTheme {
        sleepSessionData.Display(windowSize = WindowSize.MEDIUM)
    }
}

@Preview
@Composable
fun SleepSessionDataDisplayLargeScreenPreviewDarkTheme()
{
    val sleepSessionData = Sleep.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        sleepSessionData.Display(windowSize = WindowSize.MEDIUM)
    }
}

@Preview
@Composable
fun SleepStageDisplayPreview()
{
    val stage = Sleep.generateDummyData()[0].stages[0]
    BienestarEmocionalTheme {
        stage.Display()
    }
}

@Preview
@Composable
fun SleepStageDisplayPreviewDarkTheme()
{
    val stage = Sleep.generateDummyData()[0].stages[0]
    BienestarEmocionalTheme(darkTheme = true) {
        stage.Display()
    }
}