package es.upm.bienestaremocional.app.data.sleep

import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectDataClass
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

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
    val stages: List<SleepStageRecord> = listOf()
): HealthConnectDataClass

class HealthConnectSleep(private val healthConnectManager: HealthConnectManager):
    HealthConnectSource
{
    val permissions = setOf(
        HealthPermission.createReadPermission(SleepSessionRecord::class),
        HealthPermission.createReadPermission(SleepStageRecord::class))

    override suspend fun permissionsCheck() = healthConnectManager.hasAllPermissions(permissions)

    /**
     * Reads sleep sessions for the previous seven days (from yesterday) to show a week's worth of
     * sleep data.
     *
     * In addition to reading [SleepSessionRecord]s, for each session, the duration is calculated to
     * demonstrate aggregation, and the underlying [SleepStageRecord] data is also read.
     */
    override suspend fun readSource(): List<HealthConnectDataClass>
    {
        val lastDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
            .minusDays(1)
            .withHour(12)
        val firstDay = lastDay
            .minusDays(7)

        val sessions = mutableListOf<SleepSessionData>()
        val sleepSessionRequest = ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(firstDay.toInstant(), lastDay.toInstant()),
            ascendingOrder = false
        )
        val sleepSessions = healthConnectManager.readRecords(sleepSessionRequest)

        sleepSessions.records.forEach { session ->
            val sessionTimeFilter = TimeRangeFilter.between(session.startTime, session.endTime)

            val durationAggregateRequest = AggregateRequest(
                metrics = setOf(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                timeRangeFilter = sessionTimeFilter
            )
            val stagesRequest = ReadRecordsRequest(
                recordType = SleepStageRecord::class,
                timeRangeFilter = sessionTimeFilter
            )

            val aggregateResponse = healthConnectManager.aggregate(durationAggregateRequest)

            val stagesResponse = healthConnectManager.readRecords(stagesRequest)

            sessions.add(
                SleepSessionData(
                    uid = session.metadata.id,
                    title = session.title,
                    notes = session.notes,
                    startTime = session.startTime,
                    startZoneOffset = session.startZoneOffset,
                    endTime = session.endTime,
                    endZoneOffset = session.endZoneOffset,
                    duration = aggregateResponse[SleepSessionRecord.SLEEP_DURATION_TOTAL],
                    stages = stagesResponse.records
                )
            )
        }
        return sessions
    }
}

