package es.upm.bienestaremocional.data.healthconnect.sources

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import es.upm.bienestaremocional.data.healthconnect.HealthConnectSource
import java.time.Instant
import javax.inject.Inject

/**
 * Implementation of HeartRate datasource implementing [HealthConnectSource]
 * @param healthConnectClient: proportionate HealthConnect's read and write primitives
 */
class HeartRate @Inject constructor(
    private val healthConnectClient: HealthConnectClient
) : HealthConnectSource<HeartRateRecord>(healthConnectClient) {


    override val readPermissions = setOf(
        HealthPermission.getReadPermission(HeartRateRecord::class)
    )

    override suspend fun readSource(startTime: Instant, endTime: Instant): List<HeartRateRecord> {
        val request = ReadRecordsRequest(
            recordType = HeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ascendingOrder = false
        )
        val items = healthConnectClient.readRecords(request)
        return items.records
    }
}