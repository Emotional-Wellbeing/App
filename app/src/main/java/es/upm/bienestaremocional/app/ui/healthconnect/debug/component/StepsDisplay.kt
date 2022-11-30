package es.upm.bienestaremocional.app.ui.healthconnect.debug.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.health.connect.client.records.StepsRecord
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard

@Composable
fun StepsRecord.Display()
{
    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )

        Text(text = "$count pasos", color = MaterialTheme.colorScheme.onSurface)

        metadata.Display()
    }
}