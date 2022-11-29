package es.upm.bienestaremocional.app.data.healthconnect.types

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.records.metadata.Metadata
import es.upm.bienestaremocional.app.ui.component.SeriesDateTimeHeading
import es.upm.bienestaremocional.core.extraction.healthconnect.data.formatTime
import es.upm.bienestaremocional.core.ui.component.BasicCard
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset

/**
 * Represents sleep data, raw, aggregated and sleep stages, for a given [SleepSessionRecord].
 */
data class SleepSessionData(
    val uid: String,
    val title: String?,
    val notes: String?,
    val startTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endTime: Instant,
    val endZoneOffset: ZoneOffset?,
    val duration: Duration?,
    val stages: List<SleepStageRecord> = listOf(),
    override val metadata: Metadata = Metadata()
): Record
{
    @Composable
    fun Display()
    {
        //TODO add stages
        //TODO add metadata
        BasicCard {
            SeriesDateTimeHeading(
                start = startTime,
                startZoneOffset = startZoneOffset,
                end = endTime,
                endZoneOffset = endZoneOffset
            )
            duration?.let {
                val formattedDuration = duration.formatTime()
                Text(text = "Duración: $formattedDuration horas") }

            title?.let { Text(text = "Titulo: $it") }
            notes?.let { Text(text = "Notas: $it") }
            Text(text = "Uid: $uid")
        }
    }
}