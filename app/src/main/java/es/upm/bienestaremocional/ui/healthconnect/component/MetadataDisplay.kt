package es.upm.bienestaremocional.ui.healthconnect.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.metadata.Metadata
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.ui.component.DrawPair
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.formatDate
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Displays [Metadata]
 * @param widthSize: [WindowWidthSizeClass] to modify the component according to the screen
 */
@Composable
fun Metadata.Display(widthSize: WindowWidthSizeClass) {

    val time = formatDate(ZonedDateTime.ofInstant(lastModifiedTime, ZoneId.systemDefault()))

    val color = MaterialTheme.colorScheme.onSurface

    val textStyle: TextStyle = if (widthSize == WindowWidthSizeClass.Compact)
        MaterialTheme.typography.bodySmall
    else
        MaterialTheme.typography.bodyMedium

    Column {
        Text(text = stringResource(R.string.metadata), color = color)

        Column(modifier = Modifier.padding(horizontal = 16.dp))
        {
            if (id.isNotEmpty()) {
                DrawPair(key = stringResource(R.string.id), value = id, textStyle = textStyle)
            }
            if (dataOrigin.packageName.isNotEmpty()) {
                DrawPair(
                    key = stringResource(R.string.data_origin),
                    value = dataOrigin.packageName,
                    textStyle = textStyle
                )
            }

            DrawPair(key = stringResource(R.string.instant), value = time, textStyle = textStyle)

            device?.let { d ->
                d.manufacturer?.let {
                    DrawPair(
                        key = stringResource(R.string.manufacturer),
                        value = it,
                        textStyle = textStyle
                    )
                }
                d.model?.let {
                    DrawPair(
                        key = stringResource(R.string.model),
                        value = it,
                        textStyle = textStyle
                    )
                }
            }
        }
    }


}

@Preview(group = "Light Theme")
@Composable
fun MetadataPreview() {
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme {
        Surface {
            metadata.Display(widthSize = WindowWidthSizeClass.Compact)
        }
    }
}

@Preview(group = "Dark Theme")
@Composable
fun MetadataPreviewDarkTheme() {
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            metadata.Display(widthSize = WindowWidthSizeClass.Compact)
        }

    }
}

@Preview(group = "Light Theme")
@Composable
fun MetadataPreviewLargeScreen() {
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme {
        Surface {
            metadata.Display(widthSize = WindowWidthSizeClass.Medium)
        }
    }
}

@Preview(group = "Dark Theme")
@Composable
fun MetadataPreviewLargeScreenDarkTheme() {
    val metadata = HeartRate.generateDummyData()[0].metadata
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            metadata.Display(widthSize = WindowWidthSizeClass.Medium)
        }
    }
}