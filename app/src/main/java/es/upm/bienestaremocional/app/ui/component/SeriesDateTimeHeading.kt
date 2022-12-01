package es.upm.bienestaremocional.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.formatDateTime
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.Instant
import java.time.ZoneOffset

/**
 * Display DD/MM/YYYY and start and end date in text
 * @param start: Instant with the start date
 * @param startZoneOffset: ZoneOffset of the start date
 * @param end: Instant with the end date
 * @param endZoneOffset: ZoneOffset of the end date
 */
fun LazyListScope.seriesDateTimeHeading(
    start: Instant,
    startZoneOffset: ZoneOffset?,
    end: Instant,
    endZoneOffset: ZoneOffset?
) {
    item {
        SeriesDateTimeHeading(
            start = start,
            startZoneOffset = startZoneOffset,
            end = end,
            endZoneOffset = endZoneOffset)
    }
}

/**
 * Display DD/MM/YYYY and start and end date in text
 * @param start: Instant with the start date
 * @param startZoneOffset: ZoneOffset of the start date
 * @param end: Instant with the end date
 * @param endZoneOffset: ZoneOffset of the end date
 */
@Composable
fun SeriesDateTimeHeading(
    start: Instant,
    startZoneOffset: ZoneOffset?,
    end: Instant,
    endZoneOffset: ZoneOffset?
) {
    val textToDisplay = formatDateTime(start = start,
        startZoneOffset = startZoneOffset,
        end = end,
        endZoneOffset = endZoneOffset)
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(
            color = MaterialTheme.colorScheme.secondary,
            text = textToDisplay,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Display DD/MM/YYYY and start and end date in text
 * @param time: Instant with the date
 * @param zoneOffset: ZoneOffset of [time]
 */
@Composable
fun SeriesDateTimeHeading(time: Instant, zoneOffset: ZoneOffset?)
{
    val textToDisplay = formatDateTime(time = time, zoneOffset = zoneOffset)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center)
    {
        Text(
            color = MaterialTheme.colorScheme.secondary,
            text = textToDisplay,
            textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun SeriesDateTimeHeadingPreview()
{
    BienestarEmocionalTheme {
        Surface {
            LazyColumn()
            {
                val data = HeartRate.generateDummyData()[0]
                seriesDateTimeHeading(
                    start = data.startTime,
                    startZoneOffset = data.startZoneOffset,
                    end = data.endTime,
                    endZoneOffset = data.endZoneOffset
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeriesDateTimeHeadingPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            LazyColumn()
            {
                val data = HeartRate.generateDummyData()[0]
                seriesDateTimeHeading(
                    start = data.startTime,
                    startZoneOffset = data.startZoneOffset,
                    end = data.endTime,
                    endZoneOffset = data.endZoneOffset
                )
            }
        }
    }
}