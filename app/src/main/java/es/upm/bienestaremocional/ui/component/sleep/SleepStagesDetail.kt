
package es.upm.bienestaremocional.ui.component.sleep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.SleepStageRecord
import es.upm.bienestaremocional.formatDisplayTimeStartEnd
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import java.time.ZonedDateTime


@Composable
fun SleepStageDetail(stage: SleepStageRecord)
{
    Row(modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp))
    {
        val intervalLabel = formatDisplayTimeStartEnd(
            stage.startTime, stage.startZoneOffset, stage.endTime, stage.endZoneOffset)

        Text(modifier = Modifier.weight(0.5f), text = intervalLabel)
        Text(modifier = Modifier.weight(0.4f), text = stage.stage)
    }
}

@Composable
fun SleepStagesDetail(sleepStages: List<SleepStageRecord>)
{
    sleepStages.forEach { stage -> SleepStageDetail(stage) }
}

@Preview
@Composable
fun SleepStagesDetailPreview()
{
    BienestarEmocionalTheme {
        val end2 = ZonedDateTime.now()
        val start2 = end2.minusHours(1)
        val start1 = start2.minusHours(1)
        Column {
            SleepStagesDetail(
                sleepStages = listOf(
                    SleepStageRecord(
                        stage = SleepStageRecord.StageType.DEEP,
                        startTime = start2.toInstant(),
                        startZoneOffset = start2.offset,
                        endTime = end2.toInstant(),
                        endZoneOffset = end2.offset
                    ),
                    SleepStageRecord(
                        stage = SleepStageRecord.StageType.LIGHT,
                        startTime = start1.toInstant(),
                        startZoneOffset = start1.offset,
                        endTime = start2.toInstant(),
                        endZoneOffset = start2.offset
                    )
                )
            )
        }
    }
}
