package es.upm.bienestaremocional.app.data.sleep

import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.records.metadata.Metadata
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