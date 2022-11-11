package es.upm.bienestaremocional.app.data.heartrate

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManager
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectSource
import es.upm.bienestaremocional.core.extraction.healthconnect.data.linspace
import java.time.Instant
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class HealthConnectHeartrate(private val healthConnectClient: HealthConnectClient,
                             private val healthConnectManager: HealthConnectManager):
    HealthConnectSource(healthConnectManager)
{
    override val readPermissions = setOf(
        HealthPermission.createReadPermission(HeartRateRecord::class))

    val writePermissions = setOf(
        HealthPermission.createWritePermission(HeartRateRecord::class))

    override suspend fun readSource(startTime: Instant, endTime: Instant):
            List<Record>
    {
        val sessions = mutableListOf<HeartRateRecord>()

        val hearthRateRequest = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val heartrateItems = healthConnectClient.readRecords(hearthRateRequest)

        heartrateItems.records.forEach { session ->
            sessions.add(
                HeartRateRecord(
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

    suspend fun writePermissionsCheck(): Boolean =
        healthConnectManager.hasAllPermissions(writePermissions)

    suspend fun writeSource()
    {
        val records = mutableListOf<Record>()
        // Make yesterday the last day of the hr data
        val lastDay = ZonedDateTime.now().minusDays(1).truncatedTo(ChronoUnit.DAYS)

        List(5)
        { index ->
            val init = lastDay.minusDays(index.toLong())
                .withHour(Random.nextInt(0, 11))
                .withMinute(Random.nextInt(0, 60))
            val end = lastDay.minusDays(index.toLong())
                .withHour(Random.nextInt(12, 23))
                .withMinute(Random.nextInt(0, 60))
            val numberSamples = 5
            val samples = linspace(init.toInstant().epochSecond,
                end.toInstant().epochSecond,
                numberSamples)
                .map {
                instant ->
                    HeartRateRecord.Sample(
                        Instant.ofEpochSecond(instant),
                        Random.nextLong(60, 190))
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

        healthConnectClient.insertRecords(records)
    }
}