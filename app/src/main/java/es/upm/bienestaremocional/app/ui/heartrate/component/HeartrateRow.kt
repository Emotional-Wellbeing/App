package es.upm.bienestaremocional.app.ui.heartrate.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.app.data.heartrate.generateDummyData
import es.upm.bienestaremocional.core.extraction.healthconnect.data.dateTimeWithOffsetOrDefault
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Display [HeartRateRecord.Sample]
 * @param [value] indicating beatsPerMinute to show
 */
@Composable
fun HeartRateRow(value: Long) 
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Pulsaciones: $value",
            color = MaterialTheme.colorScheme.onBackground)
    }
}

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
        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        val startTime = dateTimeWithOffsetOrDefault(start, startZoneOffset)
        val endTime = dateTimeWithOffsetOrDefault(end, endZoneOffset)
        val dateLabel = dateFormatter.format(startTime)
        val startLabel = timeFormatter.format(startTime)
        val endLabel = timeFormatter.format(endTime)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(
                color = MaterialTheme.colorScheme.secondary,
                text = "$dateLabel: $startLabel - $endLabel",
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Displays a list of [HeartRateRecord] data in the [LazyColumn].
 * @param series: Data to show
 */
fun LazyListScope.heartRateSeries(series: List<HeartRateRecord>)
{
    series.forEach{ serie ->
        seriesDateTimeHeading(
            start = serie.startTime,
            startZoneOffset = serie.startZoneOffset,
            end = serie.endTime,
            endZoneOffset = serie.endZoneOffset
        )
        items(serie.samples)
        {
            HeartRateRow(it.beatsPerMinute)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateRowPreview()
{
    BienestarEmocionalTheme() {
        Surface() {
            HeartRateRow(value = 150)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateRowPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface() {
            HeartRateRow(value = 150)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeriesDateTimeHeadingPreview()
{
    BienestarEmocionalTheme() {
        Surface() {
            LazyColumn()
            {
                val data = generateDummyData()[0]
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
        Surface() {
            LazyColumn()
            {
                val data = generateDummyData()[0]
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
fun HeartRateSeriesPreview()
{
    BienestarEmocionalTheme() {
        Surface() {
            LazyColumn()
            {
                heartRateSeries(generateDummyData())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateSeriesPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface() {
            LazyColumn()
            {
                heartRateSeries(generateDummyData())
            }
        }
    }
}