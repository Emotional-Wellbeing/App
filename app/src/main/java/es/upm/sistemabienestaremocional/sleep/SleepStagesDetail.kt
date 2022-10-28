
package es.upm.sistemabienestaremocional.sleep

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.SleepStageRecord
import es.upm.sistemabienestaremocional.formatDisplayTimeStartEnd
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme
import java.time.ZonedDateTime

@Composable
fun SleepStagesDetail(sleepStages: List<SleepStageRecord>) {
    sleepStages.forEach { stage ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            val startEndLabel = formatDisplayTimeStartEnd(
                stage.startTime, stage.startZoneOffset, stage.endTime, stage.endZoneOffset
            )
            Text(
                modifier = Modifier.weight(0.5f),
                text = startEndLabel,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier
                    .weight(0.4f),
                text = stage.stage,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun SleepStagesDetailPreview() {
    SistemaBienestarEmocionalTheme {
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
