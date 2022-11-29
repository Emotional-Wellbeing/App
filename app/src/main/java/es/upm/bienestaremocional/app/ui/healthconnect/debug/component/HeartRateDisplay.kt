package es.upm.bienestaremocional.app.ui.healthconnect.debug.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        samples.forEach {
            val formattedInstant = Duration.between(startTime, it.time).formatTime()
            Text(text = "$formattedInstant: ${it.beatsPerMinute} bpm")
        }
    }
}