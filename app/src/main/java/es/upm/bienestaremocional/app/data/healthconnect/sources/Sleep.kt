package es.upm.bienestaremocional.app.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.SleepStageRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSourceInterface
import java.time.Duration
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

/**
 * Implementation of Sleep datasource implementing [HealthConnectSourceInterface]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 * @param healthConnectManager: proportionate HealthConnect's permission primitives
 */
class Sleep(private val healthConnectClient: HealthConnectClient,
            healthConnectManager: HealthConnectManagerInterface):
    HealthConnectSource(healthConnectClient,healthConnectManager)
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
            // Make yesterday the last day of the sleep data
            val lastDay = ZonedDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS)
            val notes = listOf("I slept great!",
                "I got woken up",
                "Struggled to sleep",
                "Much needed sleep",
                "Restful sleep")

            // Create 7 days-worth of sleep data
            return List(7)
            { index ->
                val wakeUp = lastDay.minusDays(index.toLong())
                    .withHour(Random.nextInt(7, 10))
                    .withMinute(Random.nextInt(0, 60))
                val bedtime = wakeUp.minusDays(1)
                    .withHour(Random.nextInt(19, 22))
                    .withMinute(Random.nextInt(0, 60))
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
         * Generates a random sleep stage for the purpose of populating data. Excludes UNKNOWN sleep stage.
         */
        private fun randomSleepStage() = listOf(
            SleepStageRecord.StageType.AWAKE,
            SleepStageRecord.StageType.DEEP,
            SleepStageRecord.StageType.LIGHT,
            SleepStageRecord.StageType.OUT_OF_BED,
            SleepStageRecord.StageType.REM,
            SleepStageRecord.StageType.SLEEPING
        ).let { stages ->
            stages[Random.nextInt(stages.size)]
        }
    }

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
        return sessions.toList()
    }

    override val writePermissions: Set<HealthPermission> = setOf(
        HealthPermission.createWritePermission(SleepSessionRecord::class),
        HealthPermission.createWritePermission(SleepStageRecord::class))
}

