
package es.upm.bienestaremocional.app.ui.healthconnect.sleep

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.SleepStageRecord
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.core.extraction.healthconnect.data.dateTimeWithOffsetOrDefault
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatDisplayTimeStartEnd
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatHoursMinutes
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.time.format.DateTimeFormatter

@Composable
private fun SleepStageDetail(stage: SleepStageRecord)
{
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp))
    {
        val intervalLabel = formatDisplayTimeStartEnd(
            stage.startTime, stage.startZoneOffset, stage.endTime, stage.endZoneOffset)

        Text(modifier = Modifier.weight(1f),
            text = intervalLabel,
            color = MaterialTheme.colorScheme.primary)
        Text(modifier = Modifier.weight(1f),
            text = stage.stage,
            color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
private fun SleepStagesDetail(sleepStages: List<SleepStageRecord>)
{
    sleepStages.forEach { stage -> SleepStageDetail(stage) }
}

/**
 * Shows label and content in row format (sleep duration, notes, etc)
 */
@Composable
private fun SleepSessionDetailRow(@StringRes labelId: Int, item: String?)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(modifier = Modifier.weight(0.5f),
            text = stringResource(id = labelId),
            color = MaterialTheme.colorScheme.primary)

        Text(modifier = Modifier.weight(0.5f),
            text = item ?: "N/A",
            color = MaterialTheme.colorScheme.secondary)
    }
}

/**
 * Creates a row to represent a single [SleepSessionData], which encompasses data for both the sleep
 * session and any fine-grained sleep stages.
 */
@Composable
fun SleepSessionRow(sessionData: SleepSessionData, startExpanded: Boolean = false)
{
    var expanded by remember { mutableStateOf(startExpanded) }
    Column(modifier = Modifier.fillMaxWidth())
    {
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
                color = MaterialTheme.colorScheme.onBackground,
                text = startDateTime.format(formatter)
            )
            //duration text
            if (!expanded)
            {
                Text(
                    modifier = Modifier.weight(0.4f),
                    text = sessionData.duration?.formatHoursMinutes() ?: stringResource(id = R.string.not_available_abbrev),
                    color = MaterialTheme.colorScheme.primary
                )
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
            Column(modifier = Modifier.fillMaxWidth())
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
    }
}


/**
 * Displays a list of [SleepSessionData] data in the [LazyColumn].
 * @param series: Data to show
 */
fun LazyListScope.sleepSeries(series: List<SleepSessionData>)
{
    if(series.isNotEmpty())
    {
        items(series.size) { SleepSessionRow(series[it]) }
    }
    else
    {
        item {
            Text(text = "No hay datos de sueño", color = MaterialTheme.colorScheme.secondary)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SleepDetailRowPreview()
{
    BienestarEmocionalTheme()
    {
        val sessionData = Sleep.generateDummyData()[0]
        Surface()
        {
            SleepSessionDetailRow(
                labelId = R.string.sleep_duration,
                item = sessionData.duration?.formatHoursMinutes()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepDetailRowPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        val sessionData = Sleep.generateDummyData()[0]
        Surface()
        {
            SleepSessionDetailRow(
                labelId = R.string.sleep_duration,
                item = sessionData.duration?.formatHoursMinutes()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepRowPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            SleepSessionRow(sessionData = Sleep.generateDummyData()[0],
                startExpanded = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepRowPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            SleepSessionRow(sessionData = Sleep.generateDummyData()[0],
                startExpanded = false)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepRowExpandedPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            SleepSessionRow(sessionData = Sleep.generateDummyData()[0],
                startExpanded = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepRowExpandedPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            SleepSessionRow(sessionData = Sleep.generateDummyData()[0],
                startExpanded = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepSeriesPreview()
{
    BienestarEmocionalTheme()
    {
        Surface()
        {
            LazyColumn()
            {
                sleepSeries(Sleep.generateDummyData())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepSeriesPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        Surface()
        {
            LazyColumn()
            {
                sleepSeries(Sleep.generateDummyData())
            }
        }
    }
}
