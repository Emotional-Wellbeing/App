package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.metadata.Metadata
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.utils.formatDate
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun Metadata.Display(windowSize: WindowSize)
{
    val time = formatDate(ZonedDateTime.ofInstant(lastModifiedTime, ZoneId.systemDefault()))
    
    val color = MaterialTheme.colorScheme.onSurface

    val textStyle : TextStyle = if (windowSize == WindowSize.COMPACT)
        MaterialTheme.typography.bodySmall
    else
        MaterialTheme.typography.bodyMedium

    Column {
        Text(text = "Metadata:", color = color)

        Column(modifier = Modifier.padding(horizontal = 16.dp))
        {
            if (id.isNotEmpty())
            {
                DrawPair(key = "Id: ", value = id, textStyle = textStyle)
            }
            if (dataOrigin.packageName.isNotEmpty())
            {
                DrawPair(key = "DataOrigin: ",
                    value = dataOrigin.packageName,
                    textStyle = textStyle)
            }

            DrawPair(key = "Instante: ", value = time, textStyle = textStyle)

            device?.let { d ->
                d.manufacturer?.let { DrawPair(key = "Fabricante: ",
                    value = it,
                    textStyle = textStyle) }
                d.model?.let { DrawPair(key = "Modelo: ",
                    value = it,
                    textStyle = textStyle) }
            }
        }
    }


}

@Preview
@Composable
fun MetadataPreview()
{
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme {
        Surface {
            metadata.Display(windowSize = WindowSize.COMPACT)
        }
    }
}

@Preview
@Composable
fun MetadataPreviewDarkTheme()
{
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            metadata.Display(windowSize = WindowSize.COMPACT)
        }

    }
}

@Preview
@Composable
fun MetadataPreviewLargeScreen()
{
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme {
        Surface {
            metadata.Display(windowSize = WindowSize.MEDIUM)
        }
    }
}

@Preview
@Composable
fun MetadataPreviewLargeScreenDarkTheme()
{
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            metadata.Display(windowSize = WindowSize.MEDIUM)
        }
    }
}