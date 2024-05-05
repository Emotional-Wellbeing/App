package es.upm.bienestaremocional.data.healthconnect.sources

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepSessionRecord.Stage
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Duration
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

/**
 * Implementation of Sleep datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */
class Sleep @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    @Named("logTag") private val logTag: String,
) : HealthConnectSource<SleepSessionData>(healthConnectClient) {
    companion object {

        private const val SLEEP_STAGE_TYPES = 8
        /**
         * Generates a week's worth of sleep data using both a [SleepSessionRecord] to describe the overall
         * period of sleep, and additionally multiple [SleepSessionRecord.Stage] periods which cover the entire
         * [SleepSessionRecord]. For the purposes of this sample, the sleep stage data is generated randomly.
         */
        fun generateDummyData(): List<SleepSessionData> {

            val notes = listOf(
                "I slept great!",
                "I got woken up",
                "Struggled to sleep",
                "Much needed sleep",
                "Restful sleep"
            )

            return List(SLEEP_STAGE_TYPES)
            { index ->
                val (bedtime, wakeUp) = generateInterval(
                    offsetDays = index.toLong() + 1,
                )
                val sleepStages = generateSleepStages(bedtime, wakeUp)
                SleepSessionData(
                    uid = "",
                    title = "Dia $index",
                    duration = Duration.of(
                        sleepStages.sumOf { it.endTime.epochSecond - it.startTime.epochSecond },
                        ChronoUnit.SECONDS
                    ),
                    notes = notes[Random.nextInt(0, notes.size)],
                    startTime = bedtime.toInstant(),
                    startZoneOffset = bedtime.offset,
                    endTime = wakeUp.toInstant(),
                    endZoneOffset = wakeUp.offset,
                    stages = sleepStages
                )
            }
        }

        /**
         * Creates a random list of sleep stages that spans the specified [start] to [end] time.
         */
        private fun generateSleepStages(start: ZonedDateTime, end: ZonedDateTime):
                List<Stage> {
            val sleepStages = mutableListOf<Stage>()
            var stageStart = start
            while (stageStart < end) {
                val stageEnd = stageStart.plusMinutes(Random.nextLong(30, 120))
                val checkedEnd = if (stageEnd > end) end else stageEnd
                sleepStages.add(
                    Stage(
                        stage = randomSleepStage(),
                        startTime = stageStart.toInstant(),
                        endTime = checkedEnd.toInstant(),
                    )
                )
                stageStart = checkedEnd
            }
            return sleepStages
        }

        /**
         * Generates a random sleep stage for the purpose of populating data.
         */
        private fun randomSleepStage() = Random.nextInt(SLEEP_STAGE_TYPES)
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(SleepSessionRecord::class)
    )

    /**
     * Reads sleep sessions for the previous seven days (from yesterday) to show a week's worth of
     * sleep data.
     *
     * In addition to reading [SleepSessionRecord]s, for each session, the duration is calculated to
     * demonstrate aggregation, and the underlying [Stage] data is also read.
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
                    stages = session.stages
                )
            )
        }
        return sessions.toList()
    }

    override val writePermissions: Set<String> = setOf(
        HealthPermission.getWritePermission(SleepSessionRecord::class),
    )
}

