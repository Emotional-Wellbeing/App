package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.data.healthconnect.HealthConnectManager
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import es.upm.bienestaremocional.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.utils.generateInterval
import java.time.Duration
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of Sleep datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */
class Sleep @Inject constructor(
    private val healthConnectClient: HealthConnectClient,
    private val healthConnectManager: HealthConnectManager
): HealthConnectSource<SleepSessionData>(healthConnectClient,healthConnectManager)
{
    companion object
    {
        /**
         * Generates a week's worth of sleep data using both a [SleepSessionRecord] to describe the overall
         * period of sleep, and additionally multiple [SleepStageRecord] periods which cover the entire
         * [SleepSessionRecord]. For the purposes of this sample, the sleep stage data is generated randomly.
         */
        fun generateDummyData(): List<SleepSessionData>
        {

            val notes = listOf("I slept great!",
                "I got woken up",
                "Struggled to sleep",
                "Much needed sleep",
                "Restful sleep")

            return List(7)
            { index ->
                val (bedtime, wakeUp) = generateInterval(
                    offsetDays = index.toLong() + 1,
                    upperBound = 14)
                val sleepStages = generateSleepStages(bedtime, wakeUp)
                SleepSessionData(
                    uid = "",
                    title = "Dia $index",
                    duration = Duration.of(
                        sleepStages.sumOf { it.endTime.epochSecond - it.startTime.epochSecond }
                        ,ChronoUnit.SECONDS),
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
                List<SleepStageRecord>
        {
            val sleepStages = mutableListOf<SleepStageRecord>()
            var stageStart = start
            while (stageStart < end) {
                val stageEnd = stageStart.plusMinutes(Random.nextLong(30, 120))
                val checkedEnd = if (stageEnd > end) end else stageEnd
                sleepStages.add(
                    SleepStageRecord(
                        stage = randomSleepStage(),
                        startTime = stageStart.toInstant(),
                        startZoneOffset = stageStart.offset,
                        endTime = checkedEnd.toInstant(),
                        endZoneOffset = checkedEnd.offset
                    )
                )
                stageStart = checkedEnd
            }
            return sleepStages
        }

        /**
         * Generates a random sleep stage for the purpose of populating data.
         */
        private fun randomSleepStage() = Random.nextInt(7)
    }

    override val readPermissions = setOf(
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getReadPermission(SleepStageRecord::class))

    /**
     * Reads sleep sessions for the previous seven days (from yesterday) to show a week's worth of
     * sleep data.
     *
     * In addition to reading [SleepSessionRecord]s, for each session, the duration is calculated to
     * demonstrate aggregation, and the underlying [SleepStageRecord] data is also read.
     */
    override suspend fun readSource(startTime: Instant, endTime: Instant): List<SleepSessionData>
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
        return sessions.toList()
    }

    override val writePermissions: Set<String> = setOf(
        HealthPermission.getWritePermission(SleepSessionRecord::class),
        HealthPermission.getWritePermission(SleepStageRecord::class))
}

