package es.upm.bienestaremocional.app.ui.healthconnect.debug.component

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
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatTime
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
fun SleepSessionData.Display()
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        title?.let { Text(text = "Titulo: $it",color = MaterialTheme.colorScheme.onSurface) }
        notes?.let { Text(text = "Notas: $it",color = MaterialTheme.colorScheme.onSurface) }

        //if (uid.isNotEmpty())
        //    Text(text = "Uid: $uid")

        duration?.let {
            val formattedDuration = duration.formatTime()
            Text(text = "Duración: $formattedDuration h",
                color = MaterialTheme.colorScheme.onSurface) }

        Text(text = "Etapas del sueño:",color = MaterialTheme.colorScheme.onSurface)
        stages.forEach{it.Display()}

        metadata.Display()
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
            color = MaterialTheme.colorScheme.secondary)
    }
}

@Preview
@Composable
fun SleepSessionDataDisplayPreview()
{
    val sleepSessionData = Sleep.generateDummyData()[0]
    BienestarEmocionalTheme {
        sleepSessionData.Display()
    }
}

@Preview
@Composable
fun SleepSessionDataDisplayPreviewDarkTheme()
{
    val sleepSessionData = Sleep.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        sleepSessionData.Display()
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