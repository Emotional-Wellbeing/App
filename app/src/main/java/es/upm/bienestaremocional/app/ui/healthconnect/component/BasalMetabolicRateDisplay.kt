package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.responsive.WindowSize

@Composable
fun BasalMetabolicRateRecord.Display(windowSize: WindowSize)
{
    val imb = String.format("%.2f",basalMetabolicRate.inKilocaloriesPerDay)
    BasicCard {
        SeriesDateTimeHeading(time = time, zoneOffset = zoneOffset)
        Text(text = "IMB: $imb kcal/día",
            color = MaterialTheme.colorScheme.onSurface)
        metadata.Display(windowSize)
    }
}