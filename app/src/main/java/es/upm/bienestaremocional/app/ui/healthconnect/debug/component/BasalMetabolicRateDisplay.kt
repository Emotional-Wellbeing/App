package es.upm.bienestaremocional.app.ui.healthconnect.debug.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard

@Composable
fun BasalMetabolicRateRecord.Display()
{
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        Text(text = "IMB: $basalMetabolicRate",
            color = MaterialTheme.colorScheme.onSurface)
        metadata.Display()
    }
}