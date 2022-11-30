package es.upm.bienestaremocional.app.ui.healthconnect.debug.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatTime
import es.upm.bienestaremocional.core.ui.component.BasicCard
import java.time.Duration

@Composable
fun HeartRateRecord.Display()
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            samples.forEach {
                val formattedInstant = Duration.between(startTime, it.time).formatTime()
                Row(modifier = Modifier.fillMaxSize())
                {
                    Text(modifier = Modifier.weight(1f),
                        text = formattedInstant,
                        color = MaterialTheme.colorScheme.primary)
                    Text(modifier = Modifier.weight(1f),
                        text = "${it.beatsPerMinute} bpm",
                        color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
        metadata.Display()
    }
}