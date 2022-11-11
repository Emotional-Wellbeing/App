
package es.upm.bienestaremocional.app.ui.sleep.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.SleepStageRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.sleep.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.data.dateTimeWithOffsetOrDefault
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatDisplayTimeStartEnd
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatHoursMinutes
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Creates a row to represent a [SleepSessionData], which encompasses data for both the sleep
 * session and any fine-grained sleep stages.
 */
@Composable
fun SleepSessionRow(sessionData: SleepSessionData, startExpanded: Boolean = false)
{
    var expanded by remember { mutableStateOf(startExpanded) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        val formatter = DateTimeFormatter.ofPattern("eee, d LLL")
        val startDateTime =
            dateTimeWithOffsetOrDefault(sessionData.startTime, sessionData.startZoneOffset)

        //date text
        Text(
            modifier = Modifier
                .weight(0.4f),
            color = MaterialTheme.colorScheme.secondary,
            text = startDateTime.format(formatter)
        )
        //duration text
        if (!expanded)
        {
            Text(
                modifier = Modifier.weight(0.4f),
                text = sessionData.duration?.formatHoursMinutes() ?: stringResource(id = R.string.not_available_abbrev))
        }
        IconButton(onClick = { expanded = !expanded })
        {
            val icon = if (expanded) Icons.Default.ArrowForward else Icons.Default.ArrowDropDown
            Icon(icon,
                contentDescription = "expand",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
    if (expanded)
    {
        val startEndLabel = formatDisplayTimeStartEnd(
            sessionData.startTime,
            sessionData.startZoneOffset,
            sessionData.endTime,
            sessionData.endZoneOffset
        )
        SleepSessionDetailRow(labelId = R.string.sleep_time, item = startEndLabel)
        SleepSessionDetailRow(
            labelId = R.string.sleep_duration,
            item = sessionData.duration?.formatHoursMinutes()
        )
        SleepSessionDetailRow(labelId = R.string.sleep_notes, item = sessionData.notes)
        if (sessionData.stages.isNotEmpty())
        {
            SleepSessionDetailRow(labelId = R.string.sleep_stages, item = "")
            SleepStagesDetail(sessionData.stages)
        }
    }
}

@Preview
@Composable
fun SleepSessionRowPreview() {
    BienestarEmocionalTheme {
        val end = ZonedDateTime.now()
        val start = end.minusHours(1)
        Column {
            SleepSessionRow(
                SleepSessionData(
                    uid = "123",
                    title = "My sleep",
                    notes = "Slept well",
                    startTime = start.toInstant(),
                    startZoneOffset = start.offset,
                    endTime = end.toInstant(),
                    endZoneOffset = end.offset,
                    duration = Duration.between(start, end),
                    stages = listOf(
                        SleepStageRecord(
                            stage = SleepStageRecord.StageType.DEEP,
                            startTime = start.toInstant(),
                            startZoneOffset = start.offset,
                            endTime = end.toInstant(),
                            endZoneOffset = end.offset
                        )
                    )
                ),
                startExpanded = true
            )
        }
    }
}