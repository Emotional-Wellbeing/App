package es.upm.bienestaremocional.app.data.heartrate

import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.core.extraction.healthconnect.data.linspace
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectDataClass
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

data class HeartrateData(
    val startTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endTime: Instant,
    val endZoneOffset: ZoneOffset?,
    val samples: List<HeartRateRecord.Sample>,
): HealthConnectDataClass

class HealthConnectHeartrate(healthConnectManager: HealthConnectManager):
    HealthConnectSource(healthConnectManager)
{
    override val permissions = setOf(
        HealthPermission.createReadPermission(HeartRateRecord::class),
        HealthPermission.createWritePermission(HeartRateRecord::class)
    )

    override suspend fun readSource(): List<HealthConnectDataClass>
    {
        val lastDay = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
            .minusDays(1)
            .withHour(12)
        val firstDay = lastDay
            .minusDays(7)

        val sessions = mutableListOf<HeartrateData>()

        val hearthRateRequest = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(firstDay.toInstant(), lastDay.toInstant()),
            ascendingOrder = false
        )
        val heartrateItems = healthConnectManager.readRecords(hearthRateRequest)

        heartrateItems.records.forEach { session ->
            sessions.add(
                HeartrateData(
                    startTime = session.startTime,
                    startZoneOffset = session.startZoneOffset,
                    endTime = session.endTime,
                    endZoneOffset = session.endZoneOffset,
                    samples = session.samples
                )
            )
        }
        return sessions
    }

    suspend fun writeSource()
    {
        val records = mutableListOf<Record>()
        // Make yesterday the last day of the hr data
        val lastDay = ZonedDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS)

        for (i in 0..5)
        {
            val init = lastDay.minusDays(i.toLong())
                .withHour(Random.nextInt(0, 11))
                .withMinute(Random.nextInt(0, 60))
            val end = lastDay.minusDays(i.toLong())
                .withHour(Random.nextInt(12, 23))
                .withMinute(Random.nextInt(0, 60))
            val numberSamples = 5
            val instants = linspace(init.toInstant().epochSecond, end.toInstant().epochSecond, numberSamples)
            val samples = instants.map { instant ->
                HeartRateRecord.Sample(Instant.ofEpochSecond(instant),Random.nextLong(60, 190) )
            }
            val hrr = HeartRateRecord(
                startTime = init.toInstant(),
                startZoneOffset = init.offset,
                endTime = end.toInstant(),
                endZoneOffset = end.offset,
                samples = samples
            )
            records.add(hrr)
        }

        healthConnectManager.insertRecords(records)
    }
}