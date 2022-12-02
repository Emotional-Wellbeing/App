package es.upm.bienestaremocional.app.ui.healthconnect.component

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
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatHoursMinutes
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import java.time.Duration

@Composable
fun HeartRateRecord.Display(windowSize: WindowSize)
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        
        if (samples.isNotEmpty())
        {
            Text(text = "Pulsaciones:",
                color = MaterialTheme.colorScheme.onSurface)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) 
            {
                samples.forEach {
                    val formattedInstant = Duration.between(startTime, it.time).formatHoursMinutes()
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
        }
        
        
        metadata.Display(windowSize)
    }
}