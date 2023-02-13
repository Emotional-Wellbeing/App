package es.upm.bienestaremocional.app.ui.healthconnect.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.HeartRateRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatHoursMinutes
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.component.DrawPair
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.Duration

@Composable
fun HeartRateRecord.Display(windowSize: WindowSize)
{
    val unit = stringResource(id = R.string.bpm)

    BasicCard {
        SeriesDateTimeHeading(
            start = startTime,
            startZoneOffset = startZoneOffset,
            end = endTime,
            endZoneOffset = endZoneOffset
        )
        
        if (samples.isNotEmpty())
        {
            Text(text = stringResource(id = R.string.heart_rate),
                color = MaterialTheme.colorScheme.onSurface)
            Column(modifier = Modifier.padding(horizontal = 16.dp)) 
            {
                samples.forEach {
                    val formattedInstant = Duration.between(startTime, it.time).formatHoursMinutes()
                    DrawPair(key = "$formattedInstant ", value = "${it.beatsPerMinute} $unit")
                }
            }
        }

        metadata.Display(windowSize)
    }
}

@Preview(group = "Light Theme")
@Composable
fun HeartRateRecordDisplayPreview()
{
    val heartRateRecord = HeartRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        heartRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun HeartRateRecordDisplayPreviewDarkTheme()
{
    val heartRateRecord = HeartRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        heartRateRecord.Display(windowSize = WindowSize.COMPACT)
    }
}
@Preview(group = "Light Theme")
@Composable
fun HeartRateRecordDisplayLargeScreenPreview()
{
    val heartRateRecord = HeartRate.generateDummyData()[0]
    BienestarEmocionalTheme {
        heartRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}
@Preview(group = "Dark Theme")
@Composable
fun HeartRateRecordDisplayLargeScreenPreviewDarkTheme()
{
    val heartRateRecord = HeartRate.generateDummyData()[0]
    BienestarEmocionalTheme(darkTheme = true) {
        heartRateRecord.Display(windowSize = WindowSize.MEDIUM)
    }
}