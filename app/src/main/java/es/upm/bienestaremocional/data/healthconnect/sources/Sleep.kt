package es.upm.bienestaremocional.data.healthconnect.sources

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import java.time.Instant
import javax.inject.Inject
import javax.inject.Named

/**
 * Implementation of Sleep datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */
class Sleep @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    @Named("logTag") private val logTag: String,
) : HealthConnectSource<SleepSessionData>(healthConnectClient) {

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getReadPermission(SleepStageRecord::class)
    )

    /**
     * Reads sleep sessions for the previous seven days (from yesterday) to show a week's worth of
     * sleep data.
     *
     * In addition to reading [SleepSessionRecord]s, for each session, the duration is calculated to
     * demonstrate aggregation, and the underlying [SleepStageRecord] data is also read.
     */
    override suspend fun readSource(startTime: Instant, endTime: Instant): List<SleepSessionData> {
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
            var aggregateResponse: AggregationResult? = null

            //Sometimes a RemoteException is produced for unknown reasons ONLY on first record...
            try {
                aggregateResponse = healthConnectClient.aggregate(durationAggregateRequest)
            }
            catch (e: Exception) {
                Log.e(logTag, "Exception during sleep aggregate:", e)
            }

            val stagesRequest = ReadRecordsRequest(
                recordType = SleepStageRecord::class,
                timeRangeFilter = sessionTimeFilter
            )

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
                    duration = aggregateResponse?.get(SleepSessionRecord.SLEEP_DURATION_TOTAL),
                    stages = stagesResponse.records
                )
            )
        }
        return sessions.toList()
    }
}

