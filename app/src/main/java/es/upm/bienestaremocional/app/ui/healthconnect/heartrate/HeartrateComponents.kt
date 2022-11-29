package es.upm.bienestaremocional.app.ui.healthconnect.heartrate

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.ui.component.seriesDateTimeHeading
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

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
fun HeartRateSeriesPreview()
{
    BienestarEmocionalTheme() {
        Surface() {
            LazyColumn()
            {
                heartRateSeries(HeartRate.generateDummyData())
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
                heartRateSeries(HeartRate.generateDummyData())
            }
        }
    }
}