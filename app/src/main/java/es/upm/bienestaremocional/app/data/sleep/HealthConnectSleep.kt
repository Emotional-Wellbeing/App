package es.upm.bienestaremocional.app.data.sleep

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import java.time.Instant

class HealthConnectSleep(private val healthConnectClient: HealthConnectClient,
                         healthConnectManager: HealthConnectManager):
    HealthConnectSource(healthConnectManager)
{
    override val readPermissions = setOf(
        HealthPermission.createReadPermission(SleepSessionRecord::class),
        HealthPermission.createReadPermission(SleepStageRecord::class))

    /**
     * Reads sleep sessions for the previous seven days (from yesterday) to show a week's worth of
     * sleep data.
     *
     * In addition to reading [SleepSessionRecord]s, for each session, the duration is calculated to
     * demonstrate aggregation, and the underlying [SleepStageRecord] data is also read.
     */
    override suspend fun readSource(startTime: Instant, endTime: Instant): List<Record>
    {
        val sessions = mutableListOf<SleepSessionData>()

        //petición de sesiones
        val sleepSessionRequest = ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val sleepSessions = healthConnectClient.readRecords(sleepSessionRequest)

        //para cada sesión, pedimos las stages y el agregado del total de sueño
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

            val aggregateResponse = healthConnectClient.aggregate(durationAggregateRequest)

            val stagesResponse = healthConnectClient.readRecords(stagesRequest)

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

