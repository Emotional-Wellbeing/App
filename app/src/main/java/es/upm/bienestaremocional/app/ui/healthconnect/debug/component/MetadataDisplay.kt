package es.upm.bienestaremocional.app.ui.healthconnect.debug.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.metadata.Metadata
import es.upm.bienestaremocional.app.formatDate
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun Metadata.Display()
{
    val time = formatDate(ZonedDateTime.ofInstant(lastModifiedTime, ZoneId.systemDefault()))

    Text(text = "Metadata:",
        color = MaterialTheme.colorScheme.onSurface)

    Column(modifier = Modifier.padding(horizontal = 16.dp))
    {
        if (id.isNotEmpty())
            Text(text = "Id: $id",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium)
        if (dataOrigin.packageName.isNotEmpty())
            Text(text = "DataOrigin: ${dataOrigin.packageName}",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium)
        Text(text = "Instante: $time",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium)
        device?.let { d ->
            d.manufacturer?.let { Text(text = "Fabricante: $it",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium) }
            d.model?.let { Text(text = "Modelo: $it",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium) }
        }
    }
}
